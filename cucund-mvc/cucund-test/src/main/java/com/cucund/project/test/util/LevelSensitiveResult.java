package com.cucund.project.test.util;

import java.util.List;

import lombok.Data;


@Data
public class LevelSensitiveResult {
	
	private Integer level;
	
	private List<SensitiveResult> result;

}
