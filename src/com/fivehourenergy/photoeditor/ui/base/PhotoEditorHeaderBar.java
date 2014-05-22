package com.fivehourenergy.photoeditor.ui.base;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.ui.LeftMenuFragment;
import com.fivehourenergy.photoeditor.ui.SlideShowFragment;

public class PhotoEditorHeaderBar implements OnClickListener {

	protected Fragment fragContainer;
	protected OnHeaderBarClickListener mListener;
	
	public ImageView btnUtility;
	public Button btnShare;
	public ImageView btnCamera;
	public ImageView imgShowMenu;
	public TextView tvTitle;
	
	public PhotoEditorHeaderBar(Fragment frag) {
		fragContainer = frag;
		if(frag instanceof OnHeaderBarClickListener){
			mListener = (OnHeaderBarClickListener) frag;
		}
	}
	
	public View onCreateHeaderView(LayoutInflater inflater,ViewGroup container) {
		if(fragContainer instanceof SlideShowFragment) return null;
		View v = inflater.inflate(R.layout.header_bar, null);
		if(fragContainer!=null && fragContainer instanceof LeftMenuFragment) return null;
		btnUtility = (ImageView) v.findViewById(R.id.btnUtility);
//		btnShare	= (Button) v.findViewById(R.id.btnShare);
		btnCamera	= (ImageView) v.findViewById(R.id.btnCamera);
		imgShowMenu = (ImageView) v.findViewById(R.id.imgShowMenu);
		tvTitle = (TextView) v.findViewById(R.id.title);
		
//		btnShare.setOnClickListener(this);
		btnCamera.setOnClickListener(this);
		imgShowMenu.setOnClickListener(this);
		btnUtility.setOnClickListener(this);
		return v;
	}
	
	public void setTitle(String title){
		tvTitle.setText(title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnUtility:
			if(mListener!=null)
				mListener.onUtilityButtonClick(v);
			break;
		/*case R.id.btnShare:
			if (mListener !=null) {
				mListener.onShareButtonClick(v);
			}
			break;*/
		case R.id.btnCamera:
			if (mListener!=null) {
				mListener.onCameraButtonClick(v);
			}
			break;
		case R.id.imgShowMenu:
			if (mListener!=null) {
				mListener.onShowMenuClick(v);
			}
			break;
		default:
			break;
		}
	}
	
	public interface OnHeaderBarClickListener{
		public void onUtilityButtonClick(View v);
		public void onShareButtonClick(View v);
		public void onCameraButtonClick(View v);
		public void onShowMenuClick(View v);
	}
}
