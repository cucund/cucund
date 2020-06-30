package com.cucund.project.db.holder;

public class DataSourceHolder {

    private static ThreadLocal<String> local = new ThreadLocal<>();


    public static String getDataSource() {
        return local.get();
    }

    public static void setDataSource(String dataSource){
        local.set(dataSource);
    }

    public static void clear(){
        local.remove();
    }
}
