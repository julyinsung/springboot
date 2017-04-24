package com.example.validation;

import java.util.Collection;
import java.util.List;

public class FormatValidation extends Validation{

	@Override
	boolean check(Files files) {
		
		//if(files instanceof JoinFile){System.out.println("instanceof JoinFile");}
		
		int lines = files.getReadAllLines().size();
		String header = files.getReadAllLines().get(0);
		String footer = files.getReadAllLines().get(lines - 1);
		List<String> body = files.getReadAllLines().subList(1, lines - 1);
		
		//for (String s : body) { System.out.println(s);	}
		
		FileInfo fileInfo = files.getFileInfo();
		
		checkHeader(header, fileInfo);
		
		checkBody(body, fileInfo);
		
		return false;
	}
	
	private boolean checkHeader(String content, FileInfo fileInfo){
		boolean result = true;
		
		String[] header = content.split("\\|");
		fileInfo.setHeader(header);
	
		int headerColumnCount = fileInfo.getContensColumnCount().get("Header");
		if(checkColumnCount(header, headerColumnCount)){
			
			Collection<Integer> charLength = fileInfo.getLengthPerHeaderColumn().values();
			if(!checkLengthPerColumn(header, charLength)){
				result = false;
			}
		} else {
			result = false;
		}
		
		return result;
	}

	private void checkBody(List<String> body, FileInfo fileInfo) {
		int bodyColumnCount = fileInfo.getContensColumnCount().get("Body");
		Collection<Integer> charLength = fileInfo.getLengthPerBodyColumn().values();
		
		int size = body.size();
		String[][] bodys = new String[size][bodyColumnCount];
		
		for (int i = 0; i < size; i++) {
			String[] line = body.get(i).split("\\|", bodyColumnCount);
			
			if(checkColumnCount(line, bodyColumnCount)){
				
				if(!checkLengthPerColumn(line, charLength)){
					line[line.length -1] = "99";	
				}
			} else {
				line[line.length -1] = "99";
			}
			bodys[i] = line;
		}
		
		fileInfo.setBody(bodys);
	}

}
