package com.fivehourenergy.photoeditor.data.quickaction;

import com.fivehourenergy.photoeditor.data.quickaction.QuickActionMenu.ActionMenu;

public class ActionMenuItem{
	
	protected ActionMenu mAction;
	protected int resIcon;
	protected String text;
	private boolean sticky;
	
	public ActionMenuItem addAction(ActionMenu actionMenu){
		mAction = actionMenu;
		return this;
	}
	
	public ActionMenuItem addResIcon(int res){
		resIcon = res;
		return this;
	}
	
	public ActionMenuItem addText(String str){
		text = str;
		return this;
	}
	
	public ActionMenu getAction(){
		return mAction;
	}
	
	public int getResIcon(){
		return resIcon;
	}
	
	public String getText(){
		return text;
	}

	public boolean isSticky() {
		return sticky;
	}

	public void setSticky(boolean sticky) {
		this.sticky = sticky;
	}
	
	
	
}
