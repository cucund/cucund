package com.cucund.project.tool.runtime.compiler;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class JavaFileCompiler {

	private static JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

	public static List<ByteJavaFileObject> loaderByFile(File file){
		//获取Java文件管理器
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		LocalJavaFileManager javaFileManager = new LocalJavaFileManager(fileManager);
		//定义要编译的源文件
		//通过源文件获取到要编译的Java类源码迭代器，包括所有内部类，其中每个类都是一个 JavaFileObject，也被称为一个汇编单元
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(file);
		//生成编译任务
		JavaCompiler.CompilationTask task = compiler.getTask(null, javaFileManager, null, null, null, compilationUnits);
		//执行编译任务
		Boolean call = task.call();
		if (!call)
			throw new RuntimeException("运行时编译失败");
		return javaFileManager.getList();
	}

	public static List<ByteJavaFileObject> loaderByString(String className, String contents) {
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		LocalJavaFileManager javaFileManager = new LocalJavaFileManager(fileManager);
		StringJavaFileObject javaFileObject = new StringJavaFileObject(className,contents);
		//生成编译任务
		JavaCompiler.CompilationTask task = compiler
				.getTask(null, javaFileManager, null, null, null, Arrays.asList(javaFileObject));
		//执行编译任务
		Boolean call = task.call();
		if (!call)
			throw new RuntimeException("运行时编译失败");
		return javaFileManager.getList();

	}
}
