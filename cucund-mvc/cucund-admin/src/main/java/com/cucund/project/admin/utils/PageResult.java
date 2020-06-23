package com.cucund.project.admin.utils;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分页返回结果
 */
public class PageResult {
	/**
	 * 当前页码
	 */
	private int pageNum;
	/**
	 * 每页数量
	 */
	private int pageSize;
	/**
	 * 记录总数
	 */
	private long totalSize;
	/**
	 * 页码总数
	 */
	private int totalPages;
	/**
	 * 分页数据
	 */
	private List<?> content;

	public static PageResult build(PageRequest pageRequest,List<?> result){
		return getPageResult(pageRequest, new PageInfo((List) result));
	}

	/**
	 * 将分页信息封装到统一的接口
	 * @param pageRequest
	 * @param pageInfo
	 * @return
	 */
	private static PageResult getPageResult(PageRequest pageRequest, PageInfo<?> pageInfo) {
		PageResult pageResult = new PageResult();
		pageResult.setPageNum(pageInfo.getPageNum());
		pageResult.setPageSize(pageInfo.getPageSize());
		pageResult.setTotalSize(pageInfo.getTotal());
		pageResult.setTotalPages(pageInfo.getPages());
		pageResult.setContent(pageInfo.getList());
		return pageResult;
	}


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
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<?> getContent() {
		return content;
	}
	public void setContent(List<?> content) {
		this.content = content;
	}
}
