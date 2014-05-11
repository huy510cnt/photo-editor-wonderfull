package com.fivehourenergy.photoeditor.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.ui.base.BasePhotoFragment;

public abstract class MenuFragment<T> extends BasePhotoFragment{

	protected ListView mListView;
	@Override
	protected View onCreateContentView(LayoutInflater inflater,
			ViewGroup container) {
		View v = inflater.inflate(R.layout.left_menu, null);
		mListView = (ListView) v.findViewById(R.id.listview);
		v.findViewById(R.id.imgAllPhotos).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAllPhotos();
			}
		});
		v.findViewById(R.id.imgFavourite).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showFavourite();
			}
		});
		show();
		return v;
	}
	
	protected abstract void show();
	protected void showAllPhotos(){
		
	}
	
	protected void showFavourite(){
		
	}
}
