package com.fivehourenergy.photoeditor.data.quickaction;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.activity.MainActivity;
import com.fivehourenergy.photoeditor.data.BaseListAdapter;

public class PhotoActionAdapter extends BaseListAdapter<ActionMenuItem>{

	public PhotoActionAdapter(Activity activity, ArrayList<ActionMenuItem> datas) {
		super(activity, datas);
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(mActivity).inflate( R.layout.action_menu_item, arg2, false );
		LinearLayout llWraper = (LinearLayout) view.findViewById(R.id.llItemWraper);
		llWraper.getLayoutParams().width = (int) ((float)MainActivity.SCREEN_WIDTH/3.5);
		llWraper.getLayoutParams().height = (int) ((float)MainActivity.SCREEN_WIDTH/3.5);
		
		ActionMenuItem item = getItem( position );

		ImageView image = (ImageView) view.findViewById( R.id.image );
		TextView text = (TextView) view.findViewById( R.id.title );

		image.setImageResource( item.getResIcon() );
		text.setText( item.getText());

		return view;
	}
	
}
