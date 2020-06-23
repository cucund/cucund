package com.cucund.project.admin.service.impl;

import com.cucund.project.admin.entity.SysDict;
import com.cucund.project.admin.dao.SysDictMapper;
import com.cucund.project.admin.service.SysDictService;
import com.cucund.project.admin.utils.ColumnFilter;
import com.cucund.project.admin.utils.PageRequest;
import com.cucund.project.admin.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictServiceImpl  implements SysDictService {

	@Autowired
	private SysDictMapper sysDictMapper;

	@Override
	public int save(SysDict record) {
		if(record.getId() == null || record.getId() == 0) {
			return sysDictMapper.insertSelective(record);
		}
		return sysDictMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(SysDict record) {
		return sysDictMapper.deleteByPrimaryKey(record.getId());
	}

	@Override
	public int delete(List<SysDict> records) {
		for(SysDict record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public SysDict findById(Long id) {
		return sysDictMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult findPage(PageRequest pageRequest) {
		ColumnFilter columnFilter = pageRequest.getColumnFilter("label");
		List<SysDict> list = null;
		if(columnFilter != null) {
			list = sysDictMapper.findPageByLabel(columnFilter.getValue());
		}else{
			list = sysDictMapper.findPage();
		}
		return PageResult.build(pageRequest,list);
	}

	@Override
	public List<SysDict> findByLable(String lable) {
		return sysDictMapper.findByLable(lable);
	}

}
