package com.fivehourenergy.photoeditor.data.fileexplorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;

import com.fivehourenergy.photoeditor.data.database.DatabaseController;
import com.fivehourenergy.photoeditor.data.model.PhotoFolderModel;
import com.fivehourenergy.photoeditor.data.model.PhotoItemModel;

public class FileExplorerManager {

	protected ArrayList<PhotoItemModel> mListAllPhotos = new ArrayList<PhotoItemModel>();
	protected ArrayList<PhotoFolderModel> mListAllFolder = new ArrayList<PhotoFolderModel>();
	
	/*
	 * Reading file paths from SDCard
	 */
	public ArrayList<PhotoItemModel> getImageFilePaths(String _fileDir) {
		ArrayList<PhotoItemModel> imageFiles = new ArrayList<PhotoItemModel>();

		File directory = new File(_fileDir);

		// check for directory
		if (directory.isDirectory()) {
			// getting list of file paths
			File[] listFiles = directory.listFiles();

			// Check for count
			if (listFiles.length > 0) {

				// loop through all files
				for (int i = 0; i < listFiles.length; i++) {

					// get file path
					File file = listFiles[i];
					// check for supported file extension
					if (IsSupportedImageFile(file.getAbsolutePath())) {
						// Add image path to array list
						PhotoItemModel model = new PhotoItemModel();
						model.photoName = file.getName();
						model.photoAbsolutePath = file.getAbsolutePath();
						model.photoDate = new Date(file.lastModified());
						model.folderPath = file.getParent();
						imageFiles.add(model);
					}
				}
			}
		}

		return imageFiles;
	}

	public void loadAllPhotos(File dir) {
	    File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
 
                if (listFile[i].isDirectory()) {
                	loadAllPhotos(listFile[i]);
 
                } else {
                    if (IsSupportedImageFile(listFile[i].getAbsolutePath()))
                    {
                    	File file = listFile[i];
						PhotoItemModel model = new PhotoItemModel();
						model.photoName = file.getName();
						model.photoAbsolutePath = file.getAbsolutePath();
						model.photoDate = new Date(file.lastModified());
						model.folderPath = file.getParent();
//						model.isFavourite = DatabaseController.getInstanceOfDataSource().isFavourite(file.getAbsolutePath());
						mListAllPhotos.add(model);
                    }
                }
 
            }
        }
    }

	public void loadAllFolder(){
		if(mListAllPhotos.size() == 0){
			loadAllPhotos(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
		}
		
		for(int i = 0;i<mListAllPhotos.size();i++){
			PhotoItemModel photoItem = mListAllPhotos.get(i);
			String folderPath = photoItem.folderPath;
			boolean isContain = false;
			for(int j = 0 ;j<mListAllFolder.size();j++){
				if(mListAllFolder.get(j).absolutePath.equals(folderPath)){
					isContain = true;
					mListAllFolder.get(j).imageList.add(photoItem);
					break;
				}
			}
			if(!isContain){
				PhotoFolderModel folderModel = new PhotoFolderModel();
				folderModel.name = new File(folderPath).getName();
				folderModel.absolutePath = folderPath;
				folderModel.imageList = new ArrayList<PhotoItemModel>();
				folderModel.imageList.add(photoItem);
				mListAllFolder.add(folderModel);
			}
		}
	}
	/*
	 * Check supported file extensions
	 * 
	 * @returns boolean
	 */
	private boolean IsSupportedImageFile(String filePath) {
		String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
				filePath.length());

		if (FileExplorerConstant.IMAGE_FILE_EXTENTION.contains(ext
				.toLowerCase(Locale.getDefault())))
			return true;
		else
			return false;

	}
}
