package com.cucund.project.admin.utils;

import com.github.pagehelper.PageHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求
 */
public class PageRequest {
	/**
	 * 当前页码
	 */
	private int pageNum = 1;
	/**
	 * 每页数量
	 */
	private int pageSize = 10;
	private Map<String, ColumnFilter> columnFilters = new HashMap<String, ColumnFilter>();

	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void startPage(){
		PageHelper.startPage(pageNum, pageSize);
	}

	public ColumnFilter getColumnFilter(String name) {
		return columnFilters.get(name);
	}
	public String getParam(String key){
		String value = null;
		ColumnFilter columnFilter = getColumnFilter(key);
		if(columnFilter != null) {
			value = columnFilter.getValue();
		}
		return value;
	}
}
