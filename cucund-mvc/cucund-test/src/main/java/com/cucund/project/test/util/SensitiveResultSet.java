package com.cucund.project.test.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveResultSet {
	
	private Integer level;
	
	private List<SensitiveResult> result;
	
	private int score = 0;

	public void addScore(int score) {
		this.score = this.score + score;
	}

}
