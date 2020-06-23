package com.cucund.project.admin.service.impl;

import com.cucund.project.admin.dao.SysRoleMapper;
import com.cucund.project.admin.dao.SysUserMapper;
import com.cucund.project.admin.dao.SysUserRoleMapper;
import com.cucund.project.admin.entity.SysMenu;
import com.cucund.project.admin.entity.SysRole;
import com.cucund.project.admin.entity.SysUser;
import com.cucund.project.admin.entity.SysUserRole;
import com.cucund.project.admin.service.SysMenuService;
import com.cucund.project.admin.service.SysUserService;
import com.cucund.project.admin.utils.PageRequest;
import com.cucund.project.admin.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Transactional
	@Override
	public int save(SysUser record) {
		Long id = null;
		if(record.getId() == null || record.getId() == 0) {
			// 新增用户
			sysUserMapper.insertSelective(record);
			id = record.getId();
		} else {
			// 更新用户信息
			sysUserMapper.updateByPrimaryKeySelective(record);
		}
		// 更新用户角色
		if(id != null && id == 0) {
			return 1;
		}
		if(id != null) {
			for(SysUserRole sysUserRole:record.getUserRoles()) {
				sysUserRole.setUserId(id);
			}
		} else {
			sysUserRoleMapper.deleteByUserId(record.getId());
		}
		for(SysUserRole sysUserRole:record.getUserRoles()) {
			sysUserRoleMapper.insertSelective(sysUserRole);
		}
		return 1;
	}

	@Override
	public int delete(SysUser record) {
		return sysUserMapper.deleteByPrimaryKey(record.getId());
	}

	@Override
	public int delete(List<SysUser> records) {
		for(SysUser record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public SysUser findById(Long id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public SysUser findByName(String name) {
		return sysUserMapper.findByName(name);
	}
	
	@Override
	public PageResult findPage(PageRequest pageRequest) {
		pageRequest.startPage();
		List<SysUser> pageResult = null;
		String name = pageRequest.getParam("name");
		String email = pageRequest.getParam( "email");
		if(name != null) {
			if(email != null) {
				pageResult = sysUserMapper.findPageByNameAndEmail( name, email);
			} else {
				pageResult = sysUserMapper.findPageByName(name);
			}
		} else {
			pageResult = sysUserMapper.findPage();
		}
		PageResult result = PageResult.build(pageRequest, pageResult);
		// 加载用户角色信息
		findUserRoles(result);
		return result;
	}

	/**
	 * 加载用户角色
	 * @param pageResult
	 */
	private void findUserRoles(PageResult pageResult) {
		List<?> content = pageResult.getContent();
		for(Object object:content) {
			SysUser sysUser = (SysUser) object;
			List<SysUserRole> userRoles = findUserRoles(sysUser.getId());
			sysUser.setUserRoles(userRoles);
			sysUser.setRoleNames(getRoleNames(userRoles));
		}
	}

	private String getRoleNames(List<SysUserRole> userRoles) {
		StringBuilder sb = new StringBuilder();
		for(Iterator<SysUserRole> iter=userRoles.iterator(); iter.hasNext();) {
			SysUserRole userRole = iter.next();
			SysRole sysRole = sysRoleMapper.selectByPrimaryKey(userRole.getRoleId());
			if(sysRole == null) {
				continue ;
			}
			sb.append(sysRole.getRemark());
			if(iter.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	@Override
	public Set<String> findPermissions(String userName) {	
		Set<String> perms = new HashSet<>();
		List<SysMenu> sysMenus = sysMenuService.findByUser(userName);
		for(SysMenu sysMenu:sysMenus) {
			if(sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms())) {
				perms.add(sysMenu.getPerms());
			}
		}
		return perms;
	}

	@Override
	public List<SysUserRole> findUserRoles(Long userId) {
		return sysUserRoleMapper.findUserRoles(userId);
	}
}
