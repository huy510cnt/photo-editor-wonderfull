package com.fivehourenergy.photoeditor.data.quickaction;

import com.fivehourenergy.photoeditor.data.model.PhotoItemModel;
import com.fivehourenergy.photoeditor.data.quickaction.QuickActionMenu.OnActionMenuClickListener;

import android.app.Activity;
import android.view.View;

public class ActionInput {

	public Activity activity;
	public View anchor;
	public int menuType;
	public int orientation;
	public PhotoItemModel data;
	public OnActionMenuClickListener listener;
}
