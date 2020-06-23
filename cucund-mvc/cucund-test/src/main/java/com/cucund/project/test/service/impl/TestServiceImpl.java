package com.cucund.project.test.service.impl;

import com.cucund.project.db.dao.DBSqlDao;
import com.cucund.project.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    DBSqlDao dbSqlDao;

    @Override
    public Map<String, Object> getData(Map<String, Object> map) {
        return dbSqlDao.selectOne4DbSql("getData",map);
    }
}
