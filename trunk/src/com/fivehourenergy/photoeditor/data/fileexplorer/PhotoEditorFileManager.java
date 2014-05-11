package com.fivehourenergy.photoeditor.data.fileexplorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

import com.fivehourenergy.photoeditor.data.model.PhotoFolderModel;
import com.fivehourenergy.photoeditor.data.model.PhotoItemModel;

public class PhotoEditorFileManager extends FileExplorerManager{

	protected PhotoFolderModel mLibraryFolder = new PhotoFolderModel();
	
	public ArrayList<PhotoItemModel> getAllPhotos(){
		if(mListAllPhotos.size() == 0)
		loadAllPhotos(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
		return mListAllPhotos;
	}
	
	public ArrayList<PhotoFolderModel> getAllFolder(){
		if(mListAllFolder.size()== 0){
			loadAllFolder();
		}
		return mListAllFolder;
	}
	
	public PhotoFolderModel getLibraryFolder(){
		if(mListAllPhotos.size() == 0){
			loadAllPhotos(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
			mLibraryFolder.name = "Library";
			mLibraryFolder.absolutePath = "";
			mLibraryFolder.imageList = mListAllPhotos;
		}
		return mLibraryFolder;
	}
	
	public void reloadData(){
		mListAllFolder = new ArrayList<PhotoFolderModel>();
		mListAllPhotos = new ArrayList<PhotoItemModel>();
		loadAllFolder();
	}
}
