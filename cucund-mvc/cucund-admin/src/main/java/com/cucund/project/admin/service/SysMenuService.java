package com.cucund.project.admin.service;


import com.cucund.project.admin.entity.SysMenu;
import com.cucund.project.admin.utils.PageRequest;
import com.cucund.project.admin.utils.PageResult;

import java.util.List;

/**
 * 菜单管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface SysMenuService {

	int save(SysMenu record);

	int delete(SysMenu record);

	int delete(List<SysMenu> records);

	SysMenu findById(Long id);

	PageResult findPage(PageRequest pageRequest);

	/**
	 * 查询菜单树,用户ID和用户名为空则查询全部
	 * @param menuType 获取菜单类型，0：获取所有菜单，包含按钮，1：获取所有菜单，不包含按钮
	 * @param userName
	 * @return
	 */
	List<SysMenu> findTree(String userName, int menuType);

	/**
	 * 根据用户名查找菜单列表
	 * @param userName
	 * @return
	 */
	List<SysMenu> findByUser(String userName);
}
