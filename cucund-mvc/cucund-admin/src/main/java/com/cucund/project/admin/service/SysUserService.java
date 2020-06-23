package com.cucund.project.admin.service;


import com.cucund.project.admin.entity.SysUser;
import com.cucund.project.admin.entity.SysUserRole;
import com.cucund.project.admin.utils.PageRequest;
import com.cucund.project.admin.utils.PageResult;

import java.util.List;
import java.util.Set;

/**
 * 用户管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface SysUserService {

	SysUser findByName(String username);

	/**
	 * 查找用户的菜单权限标识集合
	 * @param userName
	 * @return
	 */
	Set<String> findPermissions(String userName);

	/**
	 * 查找用户的角色集合
	 * @param userId
	 * @return
	 */
	List<SysUserRole> findUserRoles(Long userId);

	SysUser findById(Long id);

	int save(SysUser record);

	int delete(SysUser record);

	int delete(List<SysUser> records);

	PageResult findPage(PageRequest pageRequest);
}
