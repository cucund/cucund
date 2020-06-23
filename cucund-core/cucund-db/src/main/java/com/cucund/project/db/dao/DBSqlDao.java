package com.cucund.project.db.dao;

import com.cucund.project.db.entity.DBSql;
import com.cucund.project.db.provider.SelectProviderImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DBSqlDao extends BaseDao<DBSql>{

    @SelectProvider(type = SelectProviderImpl.class,method = "selectOne4DbSql")
    public Map<String,Object> selectOne4DbSql(@Param("sqlCode") String sqlCode,@Param("data") Map<String,Object> data);


    @SelectProvider(type = SelectProviderImpl.class,method = "selectList4DbSql")
    public List<Map<String,Object>> selectList4DbSql(@Param("sqlCode") String sqlCode, @Param("data") Map<String,Object> data);
}
