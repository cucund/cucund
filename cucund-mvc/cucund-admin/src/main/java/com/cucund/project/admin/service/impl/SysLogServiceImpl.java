package com.cucund.project.admin.service.impl;

import com.cucund.project.admin.dao.SysLogMapper;
import com.cucund.project.admin.entity.SysLog;
import com.cucund.project.admin.service.SysLogService;
import com.cucund.project.admin.utils.ColumnFilter;
import com.cucund.project.admin.utils.PageRequest;
import com.cucund.project.admin.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl  implements SysLogService {

	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public int save(SysLog record) {
		if(record.getId() == null || record.getId() == 0) {
			return sysLogMapper.insertSelective(record);
		}
		return sysLogMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(SysLog record) {
		return sysLogMapper.deleteByPrimaryKey(record.getId());
	}

	@Override
	public int delete(List<SysLog> records) {
		for(SysLog record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public SysLog findById(Long id) {
		return sysLogMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult findPage(PageRequest pageRequest) {
		ColumnFilter columnFilter = pageRequest.getColumnFilter("userName");
		List<SysLog> list = null;
		if(columnFilter != null) {
			list = sysLogMapper.findPageByUserName( columnFilter.getValue());
		}else {
			list = sysLogMapper.findPage();
		}
		return PageResult.build(pageRequest,list);
	}
	
}
