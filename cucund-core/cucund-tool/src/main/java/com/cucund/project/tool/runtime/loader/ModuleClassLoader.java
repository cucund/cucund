package com.cucund.project.tool.runtime.loader;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class ModuleClassLoader extends ClassLoader{

	private String scope;

	private Integer size; //计算类大小

	private Integer limit; //限制类加载大小

	private String router;

	private ClassLoader javaseClassLoader;

	private static final String CLASS_FILE_SUFFIX = ".class";

	public ModuleClassLoader(){
		this("default");
	}

	public ModuleClassLoader(String scope){
		this("/",scope);
	}

	public ModuleClassLoader(String router, String scope){
		this(router,scope,0,5 * 1024 *1024);
	}

	public ModuleClassLoader(String router, String scope, Integer size, Integer limit){
		this.size = size;
		this.limit = limit;
		this.scope = suffix(scope);
		this.router = suffix(router);

		ClassLoader j = String.class.getClassLoader();
		if (j == null) {
			j = getSystemClassLoader();
			while (j.getParent() != null) {
				j = j.getParent();
			}
		}
		this.javaseClassLoader = j;
	}

	private String suffix(String str){
		if(str.endsWith("/")||str.endsWith("\\"))
			return str;
		return str.replace("/","\\") +"\\";
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return this.loadClass(name,false);
	}

	private String findClasspath(String name) {

		return router + scope + name;
	}

	Map<String,byte[]> clazzMap = new HashMap<>();

	@Override
	protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {


		Class<?> c = super.findLoadedClass(className);

		if(c != null)
			return c;

		String resourceName = binaryNameToPath(className, false);

		ClassLoader javaseLoader = getJavaseClassLoader();

		boolean tryLoadingFromJavaseLoader;
		//查询本地 类加载器是否加载了类
		try {
			URL url = javaseLoader.getResource(resourceName);
			tryLoadingFromJavaseLoader = (url != null);
		} catch (Throwable t) {
			ExceptionUtils.handleThrowable(t);
			tryLoadingFromJavaseLoader = true;
		}
		if (tryLoadingFromJavaseLoader) {
			try {
				c = javaseLoader.loadClass(className);
				if (c != null) {
					if (resolve)
						resolveClass(c);
					return c;
				}
			} catch (ClassNotFoundException e) {
				// Ignore
			}
		}

		//未通过本地类加载器 加载的类,使用模组类加载器
		byte[] bytes = clazzMap.get(className);
		if(bytes == null)
			bytes = findClassToByte(findClasspath(resourceName));

		if( bytes.length + size > limit )
			throw new ClassLoaderLimitException( size , limit , bytes.length );

		c = super.defineClass( className, bytes , 0 , bytes.length );

		if (c == null)
			super.findClass(className);

		if (resolve)
			super.resolveClass(c);

		return c;
	}

	private String pathToBinaryName(String path, boolean withLeadingSlash) {

		// 1 for leading '/', 6 for ".class"
		StringBuilder binaryName = new StringBuilder(7 + path.length());
		if (withLeadingSlash) {
			binaryName.append('/');
		}
		String replace = path.replace('/', '.');
		replace = replace.replace("\\",".");
		binaryName.append(replace);
		return binaryName.toString().replace(CLASS_FILE_SUFFIX,"");
	}

	private String binaryNameToPath(String binaryName, boolean withLeadingSlash) {
		// 1 for leading '/', 6 for ".class"
		StringBuilder path = new StringBuilder(7 + binaryName.length());
		if (withLeadingSlash) {
			path.append('/');
		}
		path.append(binaryName.replace('.', '/'));
		path.append(CLASS_FILE_SUFFIX);
		return path.toString();
	}

	private byte[] findClassToByte(String classPath) throws ClassNotFoundException {

		InputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;

		try {
			inputStream = new FileInputStream(classPath);
			outputStream = new ByteArrayOutputStream();

			int temp = 0;
			while((temp = inputStream.read()) != -1){
				outputStream.write(temp);
			}

			byte[] bytes = outputStream.toByteArray();
			return bytes;
		}catch (Exception e){
			e.printStackTrace();
			throw new ClassNotFoundException(classPath);
		}finally {
			try {
				outputStream.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Class<?> findClass(String name) throws ClassNotFoundException {

		Class<?> c = super.findLoadedClass(name);

		if(c == null)
			super.findClass(name);

		return c;
	}


	public void loadScope() {
		String classpath = findClasspath("");
		readFileTree(classpath);
		fileNameList.stream().forEach(dat-> {
			try {
				loadClass(pathToBinaryName(dat,false));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
	}

	private List<String> fileNameList = new ArrayList<>();

	private void readFileTree(String classpath) {
		String rep = findClasspath("");
		File f1 = new File(classpath);
		if (f1.isDirectory()) {
			String s[] = f1.list();
			for (int i = 0; i < s.length; i++) {
				File f = new File(suffix(classpath)  + s[i]);
				String path = f.getPath();
				if (f.isDirectory()) {
					readFileTree(f.getPath());
				} else {
					log.info(path.replace(rep, "") + " 是 文件");
					if(path.endsWith(".class"))
						fileNameList.add(path.replace(rep, ""));
				}
			}
		} else {
			if(f1.getPath().endsWith(".class"))
				fileNameList.add(f1.getPath().replace(rep, ""));
			log.info(f1.getPath().replace(rep, "") + " 是 文件");
		}
		return ;
	}

	public boolean addClass(String className,byte[] bytes){
		return clazzMap.put(className,bytes)!= bytes;
	}

	protected ClassLoader getJavaseClassLoader() {
		return javaseClassLoader;
	}

}


