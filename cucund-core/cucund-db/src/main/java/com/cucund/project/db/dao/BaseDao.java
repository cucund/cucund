package com.cucund.project.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cucund.project.db.entity.BaseEntity;

public interface BaseDao<T extends BaseEntity> extends BaseMapper<T> {
}
