package com.fivehourenergy.photoeditor.ui.base;

import com.fivehourenergy.photoeditor.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

public abstract class BaseAbsListFragment extends BasePhotoFragment{

	protected AbsListView mAbsListView;
	
	@Override
	protected View onCreateContentView(LayoutInflater inflater,
			ViewGroup container) {
		if(getLayoutResourceId() != -1){
			View v = inflater.inflate(getLayoutResourceId(), null);
			mAbsListView = (AbsListView) v.findViewById(R.id.abs_listview);
			init(v);
			return v;
		}
		return null;
	}
	
	protected abstract void init(View contentView);
	protected abstract int getLayoutResourceId();
}
