package com.cucund.project.admin.service;


import com.cucund.project.admin.entity.SysLog;
import com.cucund.project.admin.utils.PageRequest;
import com.cucund.project.admin.utils.PageResult;

import java.util.List;

/**
 * 日志管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface SysLogService {

    int save(SysLog record);

    int delete(SysLog record);

    int delete(List<SysLog> records);

    SysLog findById(Long id);

    PageResult findPage(PageRequest pageRequest);
}
