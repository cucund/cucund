package com.cucund.project.tool.runtime.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class ByteJavaFileObject extends SimpleJavaFileObject {
	/**
	 * Construct a SimpleJavaFileObject of the given kind and with the
	 * given URI.
	 *
	 * @param uri  the URI for this file object
	 * @param kind the kind of this file object
	 */
	protected ByteJavaFileObject(URI uri, Kind kind) {
		super(uri, kind);
	}

	//存放编译后的字节码
	private ByteArrayOutputStream outPutStream;

	private String classname;
	public ByteJavaFileObject(String className, Kind kind) {
		super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), kind);
		this.classname = className;
	}

	//StringJavaFileManage 编译之后的字节码输出会调用该方法（把字节码输出到outputStream）
	@Override
	public OutputStream openOutputStream() {
		outPutStream = new ByteArrayOutputStream();
		return outPutStream;
	}

	//在类加载器加载的时候需要用到
	public byte[] getCompiledBytes() {
		return outPutStream.toByteArray();
	}

	public String getClassname() {
		return classname;
	}

}
