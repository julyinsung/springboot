package com.example.validation;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  등록파일에 대한 정보를 설정한다.
 *  생성자에서 FileInfo클래스를 생성하여 공통으로 쓰일 값들을 저장한다.
 *  특히 enum으로 설정한 정보는 map으로 변환하여 FileInfo클래스에 저장한다.
 *  (map으로 변환이유는 FileInfo클래스가 종속되지 않도록 하기위함이다. 
 *  만일 map으로 변환하지 않으면 JoinFile클래스의 enum타입으로 종속된다.)
 *
 * @author july
 *
 */
public class JoinFile extends Files{

	static final String folderPath = "file path";
	
	public JoinFile() {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFolderPath(folderPath);
		fileInfo.setContensColumnCount(getContensColumnCount());
		fileInfo.setLengthPerHeaderColumn(getHeaderColumnLength());
		fileInfo.setLengthPerBodyColumn(getLengthPerBodyColumn());
		super.setFileInfo(fileInfo);
	}
	
	public enum ContensColumnCount {
		Header(5) ,Body(16), Footer(2);

		int count;
		private ContensColumnCount(int count) {
			this.count = count;
		}
		
		int getValue(){
			return count;
		}
	}
	
	public enum LengthPerHeaderColumn {
		C1(1),C2(3),C3(24),C4(14),C5(1);
	
		private int length;
		private LengthPerHeaderColumn(int length) {
			this.length = length;
		}
		
		int getValue(){
			return length;
		}
	}
	
	public enum LengthPerBodyColumn {
		C1(1),C2(2),C3(16),C4(16),C5(32)
		,C6(6),C7(2),C8(2),C9(1),C10(0)
		,C11(1),C12(1),C13(1),C14(5),C15(4),C16(0);
	
		private int length;
		private LengthPerBodyColumn(int length) {
			this.length = length;
		}
		
		int getValue(){
			return length;
		}
	}
	
	private Map<String, Integer> getContensColumnCount(){
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		ContensColumnCount[] values = ContensColumnCount.values();
		for (ContensColumnCount c : values) {
			String key = c.name();
			int value = c.getValue();
			map.put(key, value);
		}
		return map;
	}
	
	private Map<String, Integer> getHeaderColumnLength(){
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		LengthPerHeaderColumn[] values = LengthPerHeaderColumn.values();
		for (LengthPerHeaderColumn c : values) {
			String key = c.name();
			int value = c.getValue();
			map.put(key, value);
		}
		return map;
	}
	
	private Map<String, Integer> getLengthPerBodyColumn(){
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		LengthPerBodyColumn[] values = LengthPerBodyColumn.values();
		for (LengthPerBodyColumn c : values) {
			String key = c.name();
			int value = c.getValue();
			map.put(key, value);
		}
		return map;
	}
}
