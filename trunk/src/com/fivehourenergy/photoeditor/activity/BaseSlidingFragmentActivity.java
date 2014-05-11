package com.fivehourenergy.photoeditor.activity;

import android.os.Bundle;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.widget.slidemenu.SlidingMenu;

public class BaseSlidingFragmentActivity extends BaseFragmentActivity{

	protected SlidingMenu mSlidingMenu;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		configSlidingMenu();
	}
	
	private void configSlidingMenu(){
		mSlidingMenu = new SlidingMenu(this);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSlidingMenu.setBehindWidthRes(R.dimen.left_menu_behind_width);
//		mSlidingMenu.setBehindWidth(SCREEN_WIDTH - getResources().getDimensionPixelSize(R.dimen.right_menu_size));
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		mSlidingMenu.setMenu(R.layout.menu_frame);
	}

	public void closeMenu(){
		mSlidingMenu.toggle();
	}
	
	public void toggleMenu(){
		mSlidingMenu.toggle();
	}

	public void disableSlideMenu(){
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	}
	
	public void enableSlideMenu(){
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}
}
