package com.fivehourenergy.photoeditor.data;

import java.io.File;
import java.util.ArrayList;

import android.util.Log;

import com.fivehourenergy.photoeditor.data.fileexplorer.PhotoEditorFileManager;
import com.fivehourenergy.photoeditor.data.model.PhotoItemModel;
import com.fivehourenergy.photoeditor.ui.PhotoLibraryAdapter.GridViewType;


/**
 * The Class DataController.
 */
public class DataController {

/** The instance. */
private static DataController INSTANCE = null;
	
	/** The m file manager. */
	private PhotoEditorFileManager mFileManager;
	
	private ArrayList<PhotoItemModel> mCurrentListPhoto;
	/**
	 * Instantiates a new data controller.
	 */
	
	private GridViewType currentGridType = GridViewType.NORMAL;
	
	private DataController(){
		
	}
	
	/**
	 * Gets the single instance of DataController.
	 *
	 * @return single instance of DataController
	 */
	public static DataController getInstance(){
		if(INSTANCE == null){
			INSTANCE = new DataController();
		}
		return INSTANCE;
	}
	
	
	/**
	 * Gets the photo editor file manager.
	 *
	 * @return the photo editor file manager
	 */
	public PhotoEditorFileManager getPhotoEditorFileManager(){
		if(mFileManager == null){
			mFileManager = new PhotoEditorFileManager();
		}
		
		return mFileManager;
	}
	
	public void setCurrentGridType(GridViewType type){
		currentGridType = type;
	}
	
	public GridViewType getCurrentGridType(){
		return currentGridType;
	}
	
	public void setCurrentListPhoto(ArrayList<PhotoItemModel> datas){
		if (datas!=null && datas.size()>0) {
			for (int i = 0; i < datas.size(); i++) {
				Log.d("Current Item "+i, ""+datas.get(i).photoAbsolutePath);
			}
		}
		mCurrentListPhoto = datas;
	}
	
	public ArrayList<PhotoItemModel> getCurrentListPhoto(){
		return mCurrentListPhoto;
	}
	
	public void reloadCurrentList(){
		if(mCurrentListPhoto!=null && mCurrentListPhoto.size()>0){
			int size = mCurrentListPhoto.size();
			for (int i = 0; i < size; i++) {
				File file = new File(mCurrentListPhoto.get(i).photoAbsolutePath);
				if(!file.exists()){
					mCurrentListPhoto.remove(i);
					size--;
					i--;
				}
			}
		}
	}
	/**
	 * Clear data.
	 */
	public void clearData(){
		mFileManager = null;
	}
}
