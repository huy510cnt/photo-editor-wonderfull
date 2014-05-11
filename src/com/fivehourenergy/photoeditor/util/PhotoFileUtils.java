package com.fivehourenergy.photoeditor.util;

public class PhotoFileUtils {

	private String currentPhotoCopyPath = "";
	private String currentFolderCopyPath = "";
	
	private static PhotoFileUtils INSTANCE = null;
	
	public static PhotoFileUtils getInstance(){
		if(INSTANCE == null){
			INSTANCE = new PhotoFileUtils();
		}
		
		return INSTANCE;
	}
	
	public void setCurrentPhotoCopyPath(String path){
		currentFolderCopyPath = path;
	}
	
	public void setCurrentFolderCopyPath(String path){
		currentFolderCopyPath = path;
	}
	
	public String getCurrentPhotoCopyPath(){
		return currentPhotoCopyPath;
	}
	
	public String getCurrentFolderCopyPath(){
		return currentFolderCopyPath;
	}
	
}
