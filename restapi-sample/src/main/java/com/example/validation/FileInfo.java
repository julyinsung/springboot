package com.example.validation;

import java.util.Map;

/**
 * 파일의 스팩정보및 상태정보를 담는다.
 * @author july
 *
 */
public class FileInfo {

	private String folderPath;
	private String[] header;
	private String[][] body;
	private String[] footer;
	private Map<String, Integer> contensColumnCount;
	private Map<String, Integer> lengthPerHeaderColumn;
	private Map<String, Integer> lengthPerBodyColumn;
	
	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String[] getHeader() {
		return header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}

	public String[][] getBody() {
		return body;
	}
	
	public void setBody(String[][] body) {
		this.body = body;
	}

	public String[] getFooter() {
		return footer;
	}

	public void setFooter(String[] footer) {
		this.footer = footer;
	}

	public Map<String, Integer> getContensColumnCount() {
		return contensColumnCount;
	}

	public void setContensColumnCount(Map<String, Integer> contensColumnCount) {
		this.contensColumnCount = contensColumnCount;
	}

	public Map<String, Integer> getLengthPerHeaderColumn() {
		return lengthPerHeaderColumn;
	}

	public void setLengthPerHeaderColumn(Map<String, Integer> lengthPerHeaderColumn) {
		this.lengthPerHeaderColumn = lengthPerHeaderColumn;
	}

	public Map<String, Integer> getLengthPerBodyColumn() {
		return lengthPerBodyColumn;
	}

	public void setLengthPerBodyColumn(Map<String, Integer> lengthPerBodyColumn) {
		this.lengthPerBodyColumn = lengthPerBodyColumn;
	}
	
}
