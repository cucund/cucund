package com.cucund.project.admin.service;


import com.cucund.project.admin.entity.SysMenu;
import com.cucund.project.admin.entity.SysRole;
import com.cucund.project.admin.entity.SysRoleMenu;
import com.cucund.project.admin.utils.PageRequest;
import com.cucund.project.admin.utils.PageResult;

import java.util.List;

/**
 * 角色管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface SysRoleService {

	int save(SysRole record);

	int delete(SysRole record);

	int delete(List<SysRole> records);

	SysRole findById(Long id);

	PageResult findPage(PageRequest pageRequest);

    /**
	 * 查询全部
	 * @return
	 */
	List<SysRole> findAll();

	/**
	 * 查询角色菜单集合
	 * @return
	 */
	List<SysMenu> findRoleMenus(Long roleId);

	/**
	 * 保存角色菜单
	 * @param records
	 * @return
	 */
	int saveRoleMenus(List<SysRoleMenu> records);

	/**
	 * 根据名称查询
	 * @param name
	 * @return
	 */
	List<SysRole> findByName(String name);

}
