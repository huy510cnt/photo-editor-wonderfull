package com.fivehourenergy.photoeditor.activity;

import java.util.ArrayList;

import android.os.Bundle;

import com.fivehourenergy.photoeditor.data.DataController;
import com.fivehourenergy.photoeditor.data.model.PhotoItemModel;
import com.fivehourenergy.photoeditor.ui.LeftMenuFragment;
import com.fivehourenergy.photoeditor.ui.PhotoLibraryFragment;
import com.fivehourenergy.photoeditor.widget.quickaction3d.PopupAlbumPhotos;

public class MainActivity extends AviaryConfigActivity{

	public int currentScreenPos = LeftMenuFragment.LIBRARY_POS;
	public PopupAlbumPhotos mPopupAlbumPhotos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initScreenSize();
		super.onCreate(savedInstanceState);
		reloadData();
		loadMenu();
		loadLibraryPhotos();
	}
	
	public void reloadData(){
		DataController.getInstance().getPhotoEditorFileManager().reloadData();
	}
	/**
	 * Show all photo.
	 */
	public void loadLibraryPhotos(){
		ArrayList<PhotoItemModel> datas = DataController.getInstance().getPhotoEditorFileManager().getAllPhotos();
		DataController.getInstance().setCurrentListPhoto(datas);
		PhotoLibraryFragment mPhotoLibraryFragment = new PhotoLibraryFragment();
		switchContent(mPhotoLibraryFragment, false);
	}
	
	/**
	 * Show menu.
	 */
	public void loadMenu(){
		LeftMenuFragment mLeftMenuFragment = new LeftMenuFragment();
		switchMenu(mLeftMenuFragment, false);
	}
	
	public void openAviaryEditor(String absolutePath){
		startFeather(absolutePath);
	}
	
	public PopupAlbumPhotos getPopupAlbumPhotos(){
		if(mPopupAlbumPhotos==null) mPopupAlbumPhotos = new PopupAlbumPhotos(this);
		return mPopupAlbumPhotos;
	}
	
	@Override
	public void onPreload() {
		mPopupAlbumPhotos.update();
	}
	
}
