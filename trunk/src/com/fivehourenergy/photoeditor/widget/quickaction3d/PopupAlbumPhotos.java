package com.fivehourenergy.photoeditor.widget.quickaction3d;

import java.util.ArrayList;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.activity.MainActivity;
import com.fivehourenergy.photoeditor.data.DataController;
import com.fivehourenergy.photoeditor.data.database.DatabaseController;
import com.fivehourenergy.photoeditor.data.model.PhotoFolderModel;
import com.fivehourenergy.photoeditor.ui.PhotoLibraryFragment;
import com.fivehourenergy.photoeditor.ui.base.BaseListAdapter;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader.IDisplayImageOption;
import com.fivehourenergy.photoeditor.widget.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PopupAlbumPhotos extends PopupWindows{

	public static final int LIBRARY_POS = -100;
	public static final int FAVOURITE_POS = -200;
	
	private int currentPosition = LIBRARY_POS;
	
	private GridView mGridView;
	private AlbumPhotosAdapter mAdapter;
	
	public PopupAlbumPhotos(Context context) {
		super(context);
		setContentView(createContentView());
	}

	private View createContentView(){
		View v = LayoutInflater.from(mContext).inflate(R.layout.popup_album_photos, null);
		mGridView = (GridView) v.findViewById(R.id.grid_view);
		mGridView.setNumColumns(3);
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
		showDataOnView();
		return v;
	}
	
	private void showDataOnView(){
		ArrayList<PhotoFolderModel> datas = DataController.getInstance().getPhotoEditorFileManager().getAllFolder();
		if(datas == null) return;
		mAdapter = new AlbumPhotosAdapter((Activity) mContext, datas);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1,
					int pos, long arg3) {
				if(pos != currentPosition){
					currentPosition = pos;
					DataController.getInstance().setCurrentListPhoto(mAdapter.getItem(pos).imageList);
					PhotoLibraryFragment library = new PhotoLibraryFragment();
					getMainActiviy().switchContent(library, false);
					getMainActiviy().currentScreenPos = currentPosition;
					dismiss();
				}
			}
		});
	}
	
	private void showAllPhotos(){
		if (currentPosition != LIBRARY_POS) {
			currentPosition = LIBRARY_POS;
			getMainActiviy().loadLibraryPhotos();
			getMainActiviy().currentScreenPos = currentPosition;
		}
		dismiss();
	}
	
	private void showFavourite(){
		if (currentPosition != FAVOURITE_POS) {
			currentPosition = FAVOURITE_POS;
			DataController.getInstance().setCurrentListPhoto(DatabaseController.getInstanceOfDataSource().getListFavourite());
			PhotoLibraryFragment library = new PhotoLibraryFragment();
			getMainActiviy().switchContent(library, false);
			getMainActiviy().currentScreenPos = currentPosition;
		}
		dismiss();
	}
	
	public void update() {
		showDataOnView();
		if (currentPosition!= LIBRARY_POS && currentPosition != FAVOURITE_POS) {
			DataController.getInstance().setCurrentListPhoto(mAdapter.getItem(currentPosition).imageList);
		}
	}
	
	private MainActivity getMainActiviy(){
		if (((Activity)mContext) instanceof MainActivity) {
			return ((MainActivity)mContext);
		}
		return null;
	}
	
	public void show(View anchor) {
		preShow();
		int[] location 		= new int[2];
		anchor.getLocationOnScreen(location);
		Rect anchorRect 	= new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] 
		                	+ anchor.getHeight());
		int xLocation = anchorRect.left;
		int yLocation = anchorRect.bottom;
		mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xLocation, yLocation);
	}
	
	
	@Override
	protected int getWindowWidth() {
		return MainActivity.SCREEN_WIDTH;
	}
	
	class AlbumPhotosAdapter extends BaseListAdapter<PhotoFolderModel>{

		private int photoSize = MainActivity.SCREEN_WIDTH/5;
		public AlbumPhotosAdapter(Activity activity,
				ArrayList<PhotoFolderModel> datas) {
			super(activity, datas);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				row = LayoutInflater.from(mActivity).inflate(R.layout.album_photo_item, null);
			}
			ImageView imageView = ViewHolder.get(row, R.id.imgAlbumItem);
			TextView tvName = ViewHolder.get(row, R.id.tName);
			
			tvName.setText(getItem(position).name);
			
			imageView.getLayoutParams().width = photoSize;
			imageView.getLayoutParams().height = photoSize;
			
			if(getItem(position).imageList!=null && getItem(position).imageList.size()>0){
//				UniversalImageLoader.getInstance().loadImageView(imageView,"file:///"+getItem(position).imageList.get(0).photoAbsolutePath, size,size);
				UniversalImageLoader.getInstance().displayImage("file:///"+getItem(position).imageList.get(0).photoAbsolutePath, imageView, IDisplayImageOption.circleOption);
			}else{
				imageView.setImageResource(R.drawable.ic_stub);
			}

			return row;
		}
		
	}
}
