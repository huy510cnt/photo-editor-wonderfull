package com.fivehourenergy.photoeditor.util;

import android.graphics.Rect;

public class PhotoEditorSizeHandler {

	private static PhotoEditorSizeHandler INSTANCE = null;
	
	private int screenWidth;
	private int screenHeight;
	private Rect headerRect;
	
	private PhotoEditorSizeHandler(){
		
	}
	
	public static PhotoEditorSizeHandler getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new PhotoEditorSizeHandler();
		}
		
		return INSTANCE;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public Rect getHeaderRect() {
		return headerRect;
	}

	public void setHeaderRect(Rect headerRect) {
		this.headerRect = headerRect;
	}
	
	
}
