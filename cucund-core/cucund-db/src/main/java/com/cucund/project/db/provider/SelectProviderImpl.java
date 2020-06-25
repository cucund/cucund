package com.cucund.project.db.provider;


import com.cucund.project.db.entity.DBSql;
import com.cucund.project.db.entity.NullSql;
import com.cucund.project.db.store.SqlStore;
import com.cucund.project.tool.utils.exception.SystemError;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Slf4j
public class SelectProviderImpl {

    public String selectOne4DbSql(@Param("sqlCode") String sqlCode, @Param("data") Map<String,Object> data) {
        DBSql dbSql = SqlStore.getInstance().get(sqlCode, "selectOne");
        if(dbSql instanceof NullSql)
            SystemError.throwException("未检测到数据操作语句");
        return dbSql.getSqlScript();
    }

    public String selectList4DbSql(@Param("sqlCode") String sqlCode, @Param("data") Map<String,Object> data) {
        DBSql dbSql = SqlStore.getInstance().get(sqlCode, "selectList");
        if(dbSql instanceof NullSql)
            SystemError.throwException("未检测到数据操作语句");
        return dbSql.getSqlScript();
    }
}
