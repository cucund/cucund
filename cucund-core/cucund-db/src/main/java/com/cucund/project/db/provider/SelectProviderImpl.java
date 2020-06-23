package com.cucund.project.db.provider;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cucund.project.db.dao.DBSqlDao;
import com.cucund.project.db.entity.DBSql;
import com.cucund.project.tool.utils.spring.SpringUtil;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public class SelectProviderImpl {

    public String selectOne4DbSql(@Param("sqlCode") String sqlCode, @Param("data") Map<String,Object> data) {
        DBSqlDao dbSqlDao = SpringUtil.getBean(DBSqlDao.class);
        DBSql dbSql = dbSqlDao.selectOne(
            new QueryWrapper<DBSql>().lambda()
                .eq(DBSql::getCode,sqlCode)
                .eq(DBSql::getType,"selectOne")
        );
        return dbSql.getSqlScript();
    }

    public String selectList4DbSql(@Param("sqlCode") String sqlCode, @Param("data") Map<String,Object> data) {
        DBSqlDao dbSqlDao = SpringUtil.getBean(DBSqlDao.class);
        DBSql dbSql = dbSqlDao.selectOne(
                new QueryWrapper<DBSql>().lambda()
                        .eq(DBSql::getCode,sqlCode)
                        .eq(DBSql::getType,"selectList")
        );
        return dbSql.getSqlScript();
    }
}
