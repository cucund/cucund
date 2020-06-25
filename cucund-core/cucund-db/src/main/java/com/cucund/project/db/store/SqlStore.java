package com.cucund.project.db.store;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cucund.project.db.dao.DBSqlDao;
import com.cucund.project.db.entity.BaseEntity;
import com.cucund.project.db.entity.DBSql;
import com.cucund.project.db.entity.NullSql;
import com.cucund.project.tool.utils.lock.KeyLock;
import com.cucund.project.tool.utils.lock.NativeLock;
import com.cucund.project.tool.utils.spring.SpringUtil;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SqlStore {

    private static volatile Map<String, DBSql> sqlMap = new ConcurrentHashMap<>();

    private String applicationName;

    private DBSqlDao dbSqlDao;


    public static SqlStore getInstance(){
        return SqlStoreSingle.getSqlStore();
    }

    public SqlStore(){
        this.applicationName = applicationName();
        this.dbSqlDao = SpringUtil.getBean(DBSqlDao.class);
        refresh();
    }

    private String applicationName(){
        Environment env = SpringUtil.getBean(Environment.class);
        String property = env.getProperty("application.name");
        return property;
    }

    public void refresh(){
        synchronized (sqlMap){
            Map<String, DBSql> temp = new HashMap<>();
            List<DBSql> list = this.dbSqlDao.selectList(
                    new QueryWrapper<DBSql>().lambda()
                    .eq(DBSql::getService,this.applicationName)
                    .eq(BaseEntity::isDel,false));
            for (DBSql dbSql: list) {
                String code = dbSql.getCode();
                temp.put(code,dbSql);
            }

            for (String code:sqlMap.keySet()) {
                DBSql dbSql = temp.get(code);
                if(dbSql==null)
                    temp.put(code, new NullSql());
            }
            sqlMap.putAll(temp);
        }
    }

    private DBSql selectOne(String code,String type){
        DBSql dbSql = this.dbSqlDao.selectOne(
                new QueryWrapper<DBSql>().lambda()
                        .eq(DBSql::getService,this.applicationName)
                        .eq(DBSql::getCode,code)
                        .eq(DBSql::getType,type));
        if (dbSql == null)
            dbSql = new NullSql();
        return dbSql;
    }
    KeyLock lock = new NativeLock();

    public DBSql get(String code,String type){
        DBSql dbSql = sqlMap.get(code);
        if(dbSql == null){
            lock.lock(code+type);
            if ((dbSql = sqlMap.get(code)) == null)
                sqlMap.put(code,dbSql = selectOne(code,type));
            lock.unlock(code+type);
        }
        return dbSql;
    }


}
class SqlStoreSingle {
    private static SqlStore sqlStore = null;

    public static SqlStore getSqlStore() {
        if(sqlStore == null){
            synchronized (SqlStoreSingle.class){
                if(sqlStore == null)
                    sqlStore = new SqlStore();
            }
        }
        return sqlStore;
    }
}
