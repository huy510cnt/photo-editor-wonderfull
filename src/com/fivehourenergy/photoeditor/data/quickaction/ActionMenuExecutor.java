package com.fivehourenergy.photoeditor.data.quickaction;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.fivehourenergy.photoeditor.PhotoEditorApp;
import com.fivehourenergy.photoeditor.data.database.DatabaseController;
import com.fivehourenergy.photoeditor.data.model.ImageInfo;
import com.fivehourenergy.photoeditor.ui.base.BasePhotoFragment;
import com.fivehourenergy.photoeditor.util.PhotoFileUtils;
import com.fivehourenergy.photoeditor.util.ShareUtils;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader.IDisplayImageOption;
import com.fivehourenergy.photoeditor.util.dialog.PhotoInfoDialog;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ActionMenuExecutor {

	public static void executeActionCamera(){
		
	}
	
	public static void executeActionNewFolder(){
		
	}
	
	public static void executeActionSortByDate(){
		
	}
	
	public static void executeActionAddFavourite(){
		
	}
	
	public static void executeActionUpdateFavourite(){
		
	}
	
	public static void executeActionPhotoInfo(Fragment frag,String absolutePath){
		Uri mImageUri = Uri.fromFile(new File(absolutePath));
		if ( null != mImageUri ) {
			ImageInfo info;
			try {
				info = new ImageInfo( frag.getActivity(), mImageUri );
			} catch ( IOException e ) {
				e.printStackTrace();
				return;
			}

			if ( null != info ) {
				new PhotoInfoDialog(frag.getActivity(), info).show();
			}
		}
	}
	
	public static void executeActionRemoveFavourite(Fragment frag,String absolutePath){
		DatabaseController.getInstanceOfDataSource().deleteFavourite(absolutePath);
		if(frag instanceof BasePhotoFragment){
			((BasePhotoFragment)frag).update();
		}
	}
	
	public static void executeActionDeletePhoto(BasePhotoFragment frag,String absolutePath){
		File file = new File(absolutePath);
		if(file.exists()){
			DatabaseController.getInstanceOfDataSource().deleteFavourite(absolutePath);
			file.delete();
			frag.getMainActiviy().onReload();
		}
	}
	
	public static void executeActionDeleteFolder(){
		
	}
	
	public static void executeActionCopyPhoto(String srcFilePath){
		PhotoFileUtils.getInstance().setCurrentPhotoCopyPath(srcFilePath);
	}
	
	public static void executeActionCopyAllPhotos(){
		
	}
	
	public static void executeActionMovePhoto(){
		
	}
	
	public static void executeActionPastePhoto(Fragment frag,String desFolderPath){
		String srcFilePath = PhotoFileUtils.getInstance().getCurrentPhotoCopyPath();
		if(srcFilePath == null || srcFilePath.equals("")) return;
		File srcFile = new File(srcFilePath);
		File destDir = new File(desFolderPath);
		try {
			FileUtils.copyFileToDirectory(srcFile, destDir);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (frag instanceof BasePhotoFragment) {
			((BasePhotoFragment)frag).getMainActiviy().onReload();
		}
	}
	
	public static void executeActionEditPhoto(){
		
	}
	
	public static void executeActionSetPassword(){
		
	}
	
	public static void executeActionEditFolder(){
		
	}
	
	public static void executeActionDesign(){
		
	}
	
	public static void executeActionGridStyle(){
		
	}
	
	public static void executeActionViewAbout(){
		
	}
	
	public static void executeActionSlideShow(){
		
	}
	
	public static void executeActionViewOrder(){
		
	}
	
	public static void executeActionViewColumn(){
		
	}
	
	public static void executeActionShare(final Activity activity,String absolutePath){
		ShareUtils.shareImage(activity, absolutePath);
	}
	
	public static void executeActionSetWallPaper(final Activity activity,String absolutePath){
		final ProgressDialog progressDialog = new ProgressDialog(activity);
		UniversalImageLoader.getInstance().loadImage("file:///"+absolutePath, IDisplayImageOption.pagerOption, new SimpleImageLoadingListener(){
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				progressDialog.show();
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				progressDialog.dismiss();
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				progressDialog.dismiss();
				WallpaperManager wm = WallpaperManager.getInstance(PhotoEditorApp.getAppContext());
				try {
					wm.setBitmap(loadedImage);
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(activity, "Set wallpaper fail", Toast.LENGTH_SHORT).show();
				}
				Toast.makeText(activity, "Set wallpaper successfull !!!", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public static void executeActionReload(){
		
	}
	
	public static void executeActionAutoSlide(){
		
	}
	
	
}
