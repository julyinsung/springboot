package com.example.validation;

public class CancelFile extends Files{

	static final String folderPath = "cancel folder path";
	
	/*
	public CancelFile() {
		super(folderPath, header_column, body_column, footer_column);
	}
	*/
	
	public CancelFile() {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFolderPath(folderPath);
		super.setFileInfo(fileInfo);
	}
}
