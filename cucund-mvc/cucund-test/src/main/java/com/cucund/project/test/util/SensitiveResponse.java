package com.cucund.project.test.util;

import java.util.ArrayList;
import java.util.List;

public class SensitiveResponse {
	
	private final String txt;
	
	private boolean success;
	
	private final List<SensitiveResultSet> list = new ArrayList<SensitiveResultSet>();
	
	private int lenght = 0;

	public SensitiveResponse(final String txt) {
		this.txt = txt;
	}

	public boolean isSuccess() {
		return success;
	}

	public int getLenght() {
		return lenght;
	}

	public SensitiveResultSet getIndex(int x){
		for (SensitiveResultSet result : list) {
			if(result.getLevel().equals(x))
				return result;
		}
		return null;
	}
	
	public void setIndex(int x,int score,SensitiveResult data){
		SensitiveResultSet set = getIndex(x);
		if(set == null)
			list.add(set = new SensitiveResultSet(x,new ArrayList<>(),score));
		List<SensitiveResult> result = set.getResult();
		set.addScore(score);
		result.add(data);
		success = false;
		lenght ++;
	}

	public List<SensitiveResultSet> getList() {
		return list;
	}

	public String getTxt() {
		return txt;
	}
	
	
}
