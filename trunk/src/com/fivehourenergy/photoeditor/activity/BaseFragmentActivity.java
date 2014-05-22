package com.fivehourenergy.photoeditor.activity;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Window;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.util.PhotoEditorSizeHandler;

public class BaseFragmentActivity extends FragmentActivity{

	/** The screen width. */
	public static int SCREEN_WIDTH;
	
	/** The screen height. */
	public static int SCREEN_HEIGHT;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		initScreenSize();
		setContentView(R.layout.slide_content);
	}
	
	/**
	 * Inits the screen size.
	 */
	protected void initScreenSize(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			SCREEN_WIDTH = size.x;
			SCREEN_HEIGHT = size.y;
		} else {
			Display display = getWindowManager().getDefaultDisplay();
			SCREEN_WIDTH = display.getWidth();
			SCREEN_HEIGHT = display.getHeight();
		}
		
		PhotoEditorSizeHandler.getInstance().setScreenHeight(SCREEN_HEIGHT);
		PhotoEditorSizeHandler.getInstance().setScreenWidth(SCREEN_WIDTH);
	}
	
	/**
     * add fragment
     * @param frag
     * @param isAddToBackStack
     */
    public void switchMenu(Fragment frag,boolean isAddToBackStack){
        FragmentTransaction fragManager = getSupportFragmentManager().beginTransaction();
        fragManager.replace(R.id.menu_frame, frag);
        if(isAddToBackStack){
            fragManager.addToBackStack(null);
        }
        fragManager.commit();
    }
    
    /**
     * add fragment with fragment name
     * @param frag
     * @param isAddToBackStack
     * @param name
     */
    public void switchMenu(Fragment frag,boolean isAddToBackStack,String name){
        FragmentTransaction fragManager = getSupportFragmentManager().beginTransaction();
        fragManager.replace(R.id.menu_frame, frag);
        if(isAddToBackStack){
            fragManager.addToBackStack(name);
        }
        fragManager.commit();
    }
    
	/**
     * add fragment
     * @param frag
     * @param isAddToBackStack
     */
    public void switchContent(Fragment frag,boolean isAddToBackStack){
        FragmentTransaction fragManager = getSupportFragmentManager().beginTransaction();
        fragManager.replace(R.id.content_frame, frag);
        if(isAddToBackStack){
            fragManager.addToBackStack(null);
        }
        fragManager.commit();
    }
    
    /**
     * add fragment with fragment name
     * @param frag
     * @param isAddToBackStack
     * @param name
     */
    public void switchContent(Fragment frag,boolean isAddToBackStack,String name){
        FragmentTransaction fragManager = getSupportFragmentManager().beginTransaction();
        fragManager.replace(R.id.content_frame, frag);
        if(isAddToBackStack){
            fragManager.addToBackStack(name);
        }
        fragManager.commit();
    }
    
    /**
     * add fragment
     * @param frag
     * @param isAddToBackStack
     */
    public void addContent(Fragment frag,boolean isAddToBackStack){
        FragmentTransaction fragManager = getSupportFragmentManager().beginTransaction();
        fragManager.add(R.id.content_frame, frag);
        if(isAddToBackStack){
            fragManager.addToBackStack(null);
        }
        fragManager.commit();
    }
    
    /**
     * add fragment with fragment name
     * @param frag
     * @param isAddToBackStack
     * @param name
     */
    public void addContent(Fragment frag,boolean isAddToBackStack,String name){
        FragmentTransaction fragManager = getSupportFragmentManager().beginTransaction();
        fragManager.add(R.id.content_frame, frag);
        if(isAddToBackStack){
            fragManager.addToBackStack(name);
        }
        fragManager.commit();
    }
}
