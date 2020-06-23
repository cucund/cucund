package com.cucund.project.admin.service;


import com.cucund.project.admin.entity.SysDict;
import com.cucund.project.admin.utils.PageRequest;
import com.cucund.project.admin.utils.PageResult;

import java.util.List;

/**
 * 字典管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface SysDictService{

	int save(SysDict record);

	int delete(SysDict record);

	int delete(List<SysDict> records);

	SysDict findById(Long id);

	PageResult findPage(PageRequest pageRequest);

	/**
	 * 根据名称查询
	 * @param lable
	 * @return
	 */
	List<SysDict> findByLable(String lable);
}
