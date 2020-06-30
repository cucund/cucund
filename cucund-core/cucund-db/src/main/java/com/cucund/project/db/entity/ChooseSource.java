package com.cucund.project.db.entity;

import com.cucund.project.db.properties.DataSourceProperties;
import lombok.Data;

import java.util.List;


@Data
public class ChooseSource {

    private List<DataSourceInner> dataSources;

    private String model;

}
