package com.fivehourenergy.photoeditor.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.data.BaseListAdapter;
import com.fivehourenergy.photoeditor.data.DataController;
import com.fivehourenergy.photoeditor.data.database.DatabaseController;
import com.fivehourenergy.photoeditor.data.model.PhotoFolderModel;
import com.fivehourenergy.photoeditor.ui.PhotoLibraryFragment;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader.IDisplayImageOption;
import com.fivehourenergy.photoeditor.widget.ViewHolder;

public class LeftMenuFragment extends MenuFragment<PhotoFolderModel>{

	public static final int LIBRARY_POS = -1000;
	public static final int FAVOURITE_POS = -2000;
	
	private int currentPosition = LIBRARY_POS;
	private LeftMenuAdapter mAdapter;
	@Override
	protected void show() {
		ArrayList<PhotoFolderModel> datas = DataController.getInstance().getPhotoEditorFileManager().getAllFolder();
		if(datas!=null){
			mAdapter = new LeftMenuAdapter(getActivity(),datas);
			mListView.setCacheColorHint(0);
			mListView.setAdapter(mAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapterView, View arg1,
						int pos, long arg3) {
						if(pos != currentPosition){
							currentPosition = pos;
							DataController.getInstance().setCurrentListPhoto(mAdapter.getItem(pos).imageList);
							PhotoLibraryFragment library = new PhotoLibraryFragment();
							getMainActiviy().switchContent(library, false);
							getMainActiviy().currentScreenPos = currentPosition;
						}
						getMainActiviy().closeMenu();
				}
			});
		}
	}
	
	@Override
	protected void showAllPhotos() {
		if (currentPosition != LIBRARY_POS) {
			currentPosition = LIBRARY_POS;
			getMainActiviy().loadLibraryPhotos();
			getMainActiviy().currentScreenPos = currentPosition;
		}
		getMainActiviy().closeMenu();
	}
	
	@Override
	protected void showFavourite() {
		if (currentPosition != FAVOURITE_POS) {
			currentPosition = FAVOURITE_POS;
			DataController.getInstance().setCurrentListPhoto(DatabaseController.getInstanceOfDataSource().getListFavourite());
			PhotoLibraryFragment library = new PhotoLibraryFragment();
			getMainActiviy().switchContent(library, false);
			getMainActiviy().currentScreenPos = currentPosition;
		}
		getMainActiviy().closeMenu();
	}
	
	@Override
	public void update() {
		show();
		if (currentPosition!= LIBRARY_POS && currentPosition != FAVOURITE_POS) {
			DataController.getInstance().setCurrentListPhoto(mAdapter.getItem(currentPosition).imageList);
		}
	}
	
	public class LeftMenuAdapter extends BaseListAdapter<PhotoFolderModel>{
		
		public LeftMenuAdapter(Activity activity,
				ArrayList<PhotoFolderModel> datas) {
			super(activity, datas);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				row = LayoutInflater.from(mActivity).inflate(R.layout.menu_item1, null);
			}
			ImageView imageView = ViewHolder.get(row, R.id.imgPhotoItem);
			TextView tvName = ViewHolder.get(row, R.id.tvName);
			
			tvName.setText(getItem(position).name);
			
			int size = mActivity.getResources().getDimensionPixelSize(R.dimen.left_menu_image_folder_size);
			imageView.getLayoutParams().width = size;
			imageView.getLayoutParams().height = size;
			
			if(getItem(position).imageList!=null && getItem(position).imageList.size()>0){
//				UniversalImageLoader.getInstance().loadImageView(imageView,"file:///"+getItem(position).imageList.get(0).photoAbsolutePath, size,size);
				UniversalImageLoader.getInstance().displayImage("file:///"+getItem(position).imageList.get(0).photoAbsolutePath, imageView, IDisplayImageOption.circleOption);
			}else{
				imageView.setImageResource(R.drawable.ic_stub);
			}

			return row;
		}
	}

	@Override
	protected String getTitle() {
		return null;
	}
}
