package com.cucund.project.db.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "spring.datasource")
public class ChooseDataSourceProperties {

    private String name;

    private String type;

    private String model;

    private List<DataSourceProperties> dynamic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataSourceProperties> getDynamic() {
        return dynamic;
    }

    public void setDynamic(List<DataSourceProperties> dynamic) {
        this.dynamic = dynamic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
