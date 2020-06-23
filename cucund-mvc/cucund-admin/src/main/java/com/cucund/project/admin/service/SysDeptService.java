package com.cucund.project.admin.service;


import com.cucund.project.admin.entity.SysDept;
import com.cucund.project.admin.utils.PageRequest;
import com.cucund.project.admin.utils.PageResult;

import java.util.List;

/**
 * 机构管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface SysDeptService {

	int save(SysDept record);

	int delete(SysDept record);

	int delete(List<SysDept> records);

	SysDept findById(Long id);

	PageResult findPage(PageRequest pageRequest);

	/**
	 * 查询机构树
	 * @return
	 */
	List<SysDept> findTree();
}
