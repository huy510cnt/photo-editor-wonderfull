package com.fivehourenergy.photoeditor.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

/**
 * dialog with no title bar
 * @author HuanND
 * @since 21.01.2014
 *
 */
public abstract class BaseDialogNoTitle extends Dialog{

	protected Context		 context;
	protected Object		 mDatas;
	
	public BaseDialogNoTitle(Context context,Object datas) {
		super(context);
		mDatas = datas;
		this.context = context;
		setConfigDialog();
	}
	
	protected void setConfigDialog(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        
        //set content view for dialog
        LayoutInflater inflater = getLayoutInflater();
        View contentView = inflater.inflate(getContentResId(), null);
        setContentView(contentView);
        initContentView(inflater, contentView);
	}
	
	public LayoutInflater getLayoutInflater(){
		return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	protected abstract int getContentResId();
	protected abstract void initContentView(LayoutInflater inflater,final View contentView);
}
