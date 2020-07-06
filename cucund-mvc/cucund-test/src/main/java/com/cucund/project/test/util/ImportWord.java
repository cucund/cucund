package com.cucund.project.test.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportWord {
	
	private String word;
	
	private int level;
	
	private int score;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImportWord other = (ImportWord) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}
	
	

}
