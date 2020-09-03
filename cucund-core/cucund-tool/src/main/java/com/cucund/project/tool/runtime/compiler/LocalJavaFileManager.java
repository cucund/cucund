package com.cucund.project.tool.runtime.compiler;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalJavaFileManager extends ForwardingJavaFileManager {

	List<ByteJavaFileObject> list = new ArrayList<>();

	public LocalJavaFileManager(JavaFileManager fileManager) {
		super(fileManager);
	}

	//获取输出的文件对象，它表示给定位置处指定类型的指定类。
	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
		ByteJavaFileObject javaFileObject = new ByteJavaFileObject(className, kind);
		list.add(javaFileObject);
		return javaFileObject;
	}

	public List<ByteJavaFileObject> getList() {
		return list;
	}
}
