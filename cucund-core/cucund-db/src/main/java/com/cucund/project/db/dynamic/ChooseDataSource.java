package com.cucund.project.db.dynamic;

import com.cucund.project.db.holder.DataSourceHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


@Slf4j
public class ChooseDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        log.info(" 当前数据源-----: "+DataSourceHolder.getDataSource());
        return DataSourceHolder.getDataSource();
    }
}
