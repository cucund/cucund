package com.cucund.project.test.util;

import lombok.Data;

import java.util.Set;


@Data
public class Word {
	
	private char word;
	
	private int level;
	
	private int score;
	
	private boolean isEnd = false;
	
	private Set<Word> nextNode;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (word != other.word)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + word;
		return result;
	}

	public Word(char word, int level, boolean isEnd) {
		super();
		this.word = word;
		this.level = level;
		this.isEnd = isEnd;
	}

	public Word(char word, int level) {
		super();
		this.word = word;
		this.level = level;
	}

	public Word() {
		super();
	}

	public Word(char word) {
		super();
		this.word = word;
	}
	
	
	
}
