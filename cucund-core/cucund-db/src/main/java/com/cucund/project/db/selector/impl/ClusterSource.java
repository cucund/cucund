package com.cucund.project.db.selector.impl;

import com.cucund.project.db.entity.ChooseSource;
import com.cucund.project.db.entity.DataSourceInner;
import com.cucund.project.db.entity.ProxySource;
import com.cucund.project.db.holder.DataSourceHolder;
import com.cucund.project.db.selector.SourceStrategy;
import com.cucund.project.tool.utils.exception.SystemError;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 */
@Slf4j
public class ClusterSource implements SourceStrategy {
    @Override
    public boolean condition(ProxySource proxySource) {
        return "cluster".equals(proxySource.getChooseSource().getModel());
    }

    private List<String> regexList = Arrays.asList("select(.*)","get(.*)","find(.*)");

    private List<DataSourceInner> readList = new ArrayList<>();
    private List<DataSourceInner> writeList = new ArrayList<>();
    private boolean init = false;

    @Override
    public Void doSomething(ProxySource proxySource) {
        ChooseSource chooseSource = proxySource.getChooseSource();
        JoinPoint joinPoint = proxySource.getJoinPoint();
        String name = joinPoint.getSignature().getName();
        log.info("方法名:"+name);
        boolean isMatches = false;
        for (String regex:regexList) {
            if(name.matches(regex)){
                isMatches = true;
                break;
            }
        }
        initList(chooseSource);
        if(isMatches){
            loopReadSource();
        }else {
            findWriteSource();
        }
        return null;
    }

    private static AtomicInteger integer = new AtomicInteger(1);

    private void initList(ChooseSource chooseSource){
        if(!init){
            synchronized (this){
                if(!init) {
                    if(chooseSource.getDataSources().size() == 1){
                        DataSourceInner dataSourceInner = chooseSource.getDataSources().get(0);
                        readList.add(dataSourceInner);
                        writeList.add(dataSourceInner);
                    }
                    for (DataSourceInner dataSourceInner : chooseSource.getDataSources()) {
                        if ("write".equals(dataSourceInner.getType())) {
                            writeList.add(dataSourceInner);
                        } else if ("read".equals(dataSourceInner.getType())) {
                            readList.add(dataSourceInner);
                        }
                    }
                    if(readList.size() == 0)
                        readList.add(writeList.get(0));
                }
                init = true;
            }
        }
    }

    private void loopReadSource() {
        if(Integer.valueOf(0).equals(readList.size()))
            SystemError.throwException("数据源异常配置 无读库设置");
        if(Integer.valueOf(1).equals(readList.size())){
            DataSourceHolder.setDataSource(readList.get(0).getName());
            return;
        }
        int i = (integer.getAndIncrement() % readList.size());
        DataSourceHolder.setDataSource(readList.get(i).getName());
    }

    private void findWriteSource() {
        DataSourceHolder.setDataSource(writeList.get(0).getName());
    }
}
