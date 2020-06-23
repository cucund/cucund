package com.cucund.project.db.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("db_sql")
public class DBSql extends BaseEntity{

    private String name;

    private String code;

    private String type;

    private String sqlScript;

}
