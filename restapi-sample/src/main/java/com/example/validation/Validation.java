package com.example.validation;

import java.util.Collection;

public abstract class Validation {

	abstract boolean check(Files files);
	
	boolean checkColumnCount(String[] line, int columnCount) {
		boolean result = true;
		
		if(line.length != columnCount){
			result = false;
		}
		
//		System.out.println("checkColumnCount length >>> source:"+line.length + ", spec: "+columnCount+", result: "+result);
		
		return result;
	}

	boolean checkLengthPerColumn(String[] line, Collection<Integer> length) {
		boolean result = true;
		
		int i = 0;
		for (Integer lengthPerColumn : length) {
			
//			System.out.println("Char length >>> source:"+line[i].length() + ", spec: "+lengthPerColumn);
			
			if(line[i].length() != lengthPerColumn){
				result = false;
				break;
			}
			i += 1;
		}
		
//		System.out.println("checkLengthPerColumn: "+result+"\n\n");
		
		return result;
	}
}
