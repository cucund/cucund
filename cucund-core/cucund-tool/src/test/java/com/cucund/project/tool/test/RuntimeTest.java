package com.cucund.project.tool.test;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.cucund.project.tool.runtime.compiler.ByteJavaFileObject;
import com.cucund.project.tool.runtime.compiler.JavaFileCompiler;
import com.cucund.project.tool.runtime.loader.ModuleClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class RuntimeTest {

//	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//		String path = "\\READ\\DATA";
//		String scope = "1000";
//		ModuleClassLoader classLoader = new ModuleClassLoader(path,scope);
//		classLoader.loadScope();
//		Class<?> userClass = classLoader.findClass("com.module.domain.entity.User");
//		Object user = userClass.newInstance();
//		Method method = user.getClass().getMethod("setUsername", String.class);
//		method.invoke(user,"123");
//		System.out.println(user);
//	}
	static ModuleClassLoader classLoader = new ModuleClassLoader();

	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		List<ByteJavaFileObject> list = null;//JavaFileCompiler.loaderByFile(new File("\\READ\\DATA\\1000\\User.java"));
		String className = "com.module.domain.entity.User";
		String content = "package com.module.domain.entity;\n" +
				"\n" +
				"public class User {\n" +
				"\n" +
				"\tprivate String username;\n" +
				"\n" +
				"\tpublic String getUsername() {\n" +
				"\t\treturn username;\n" +
				"\t}\n" +
				"\n" +
				"\tpublic void setUsername(String username) {\n" +
				"\t\tthis.username = username;\n" +
				"\t}\n" +
				"}\n";
		list = JavaFileCompiler.loaderByString(className,content);
		list.stream().forEach(dat->{
			byte[] compiledBytes = dat.getCompiledBytes();
			String name = dat.getClassname();
			classLoader.addClass(name,compiledBytes);
		});
		Class<?> userClass = classLoader.loadClass(className);

		Object user = userClass.newInstance();
		Method method = user.getClass().getMethod("setUsername", String.class);
		method.invoke(user,"123");
		String jsonStr = new JSONObject(user).toString();
		System.out.println("手动装载运行时对象:"+ jsonStr);
		Object o = new JSONObject(jsonStr).toBean(userClass);
		System.out.println("JSON序列化,装载运行时对象:"+new JSONObject(user).toString());
	}

}
