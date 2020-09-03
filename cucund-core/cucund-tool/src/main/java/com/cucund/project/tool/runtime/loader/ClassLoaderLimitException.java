package com.cucund.project.tool.runtime.loader;

public class ClassLoaderLimitException extends RuntimeException {

	private Integer size;

	private Integer limit;

	private Integer nowSize;

	public ClassLoaderLimitException(Integer size, Integer limit, Integer nowSize) {
		super("类加载器,类已达到上限: 已占空间:" + size + ",限制大小:" + limit +",当前新增文件大小:" + nowSize);
	}
}
