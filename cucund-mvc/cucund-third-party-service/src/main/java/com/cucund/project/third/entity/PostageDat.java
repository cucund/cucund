package com.cucund.project.third.entity;


import com.cucund.project.db.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PostageDat extends BaseEntity {

    private String type;

    private BigDecimal cost;

    private BigDecimal minCost;

    private boolean unlimited;

}
