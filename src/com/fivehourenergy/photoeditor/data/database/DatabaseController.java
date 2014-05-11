package com.fivehourenergy.photoeditor.data.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fivehourenergy.photoeditor.PhotoEditorApp;
import com.fivehourenergy.photoeditor.data.model.PhotoItemModel;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseController.
 */
public class DatabaseController {

	/** The Constant COMIC_FAVOURITE. */
	private static final int 			COMIC_FAVOURITE = 1;
	
	/** The m data base. */
	private static SQLiteDatabase 		mDataBase;
	
	/** The m open heper. */
	private static DatabaseHelper 		mOpenHeper;
	
	/** The me. */
	private static DatabaseController 	me;
	
	/** The m context. */
	private Context mContext;

	/**
	 * Instantiates a new database controller.
	 *
	 * @param context the context
	 */
	public DatabaseController(Context context) {

		if (mOpenHeper == null) {

			this.mContext = context;
			mOpenHeper = new DatabaseHelper(mContext);

			me = this;
		}
	}

	/**
	 * Gets the instance of data source.
	 *
	 * @param context the context
	 * @return the instance of data source
	 */
	public static DatabaseController getInstanceOfDataSource() {
		if (me == null) {
			me = new DatabaseController(PhotoEditorApp.getAppContext());
		}
		return me;
	}

	
	/**
	 * Open.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void open() throws SQLException {
		// if(database == null || !database.isOpen())
		mDataBase = mOpenHeper.getWritableDatabase();
	}

	
	/**
	 * Close.
	 */
	public void close() {
		mOpenHeper.close();
	}
	
	/**
	 * Insert favourite.
	 *
	 * @param model the model
	 * @return the long
	 */
	public long insertFavourite(PhotoItemModel model){
		if(getPhotoItemByPath(model.photoAbsolutePath) != null) return -1;
		open();
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.PHOTO_NAME, model.photoName);
		values.put(DatabaseHelper.PHOTO_PATH, model.photoAbsolutePath);
		values.put(DatabaseHelper.PHOTO_FOLDER_PATH, model.folderPath);
		values.put(DatabaseHelper.PHOTO_DATE_MODIFIED, model.getDateString());
		long rowID = mDataBase.insert(DatabaseHelper.FAVOURITE_TABLE, null, values);
		close();
		return rowID;
	}
	
	/**
	 * Update favourite.
	 *
	 * @param model the model
	 * @param id the id
	 * @return the int
	 */
	public int updateFavourite(PhotoItemModel model,int id){
		open();
		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.PHOTO_NAME, model.photoName);
		values.put(DatabaseHelper.PHOTO_PATH, model.photoAbsolutePath);
		values.put(DatabaseHelper.PHOTO_FOLDER_PATH, model.folderPath);
		values.put(DatabaseHelper.PHOTO_DATE_MODIFIED, model.getDateString());
		
		int rowCount =mDataBase.update(DatabaseHelper.FAVOURITE_TABLE,
				values,
				DatabaseHelper.PHOTO_ID +" = '"+id+"'",
				null);
		close();
		return rowCount;
	}
	
	public boolean deleteFavourite(int id){
		open();
		int row = mDataBase.delete(DatabaseHelper.FAVOURITE_TABLE, DatabaseHelper.PHOTO_ID +" = '"+id+"'", null);
		close();
		if (row>0) return true;
		return false;
	}
	
	
	public boolean deleteFavourite(String absolutePath){
		PhotoItemModel item = getPhotoItemByPath(absolutePath);
		if(item!=null){
			return deleteFavourite(item.id);
		}
		return false;
	}
	/**
	 * Gets the list favourite.
	 *
	 * @return the list favourite
	 */
	public ArrayList<PhotoItemModel> getListFavourite(){
		ArrayList<PhotoItemModel> listCategory = new ArrayList<PhotoItemModel>();
		open();
		Cursor cusor = mDataBase.query(DatabaseHelper.FAVOURITE_TABLE,null, 
									   null, null, null, null, null);
		
		if(cusor != null && cusor.getCount()>0) {
			cusor.moveToFirst();
			int colID 				= cusor.getColumnIndex(DatabaseHelper.PHOTO_ID);
			int colName 			= cusor.getColumnIndex(DatabaseHelper.PHOTO_NAME);
			int colPath 			= cusor.getColumnIndex(DatabaseHelper.PHOTO_PATH);
			int colFolderPath 		= cusor.getColumnIndex(DatabaseHelper.PHOTO_FOLDER_PATH);
			int colDate				= cusor.getColumnIndex(DatabaseHelper.PHOTO_DATE_MODIFIED);
			while(! cusor.isAfterLast()) {
				PhotoItemModel model = new PhotoItemModel();
				
				model.id = cusor.getInt(colID);
				model.photoName = cusor.getString(colName);
				model.photoAbsolutePath = cusor.getString(colPath);
				model.folderPath = cusor.getString(colFolderPath);
				String strDate = cusor.getString(colDate);
				model.photoDate = model.stringToDate(strDate);
				listCategory.add(model);
				
				cusor.moveToNext();
			}
			cusor.close();
		}
		
		close();
		return listCategory;
	}
	
	/**
	 * Gets the photo item by path.
	 *
	 * @param absolutePath the absolute path
	 * @return the photo item by path
	 */
	public PhotoItemModel getPhotoItemByPath(String absolutePath){
		PhotoItemModel photoItem = null;
		open();
		Cursor cusor = mDataBase.query(DatabaseHelper.FAVOURITE_TABLE,null, 
				DatabaseHelper.PHOTO_PATH +" = '"+absolutePath+"'", null, null, null, null);
		if(cusor != null && cusor.getCount()>0) {
			cusor.moveToFirst();
			int colID 				= cusor.getColumnIndex(DatabaseHelper.PHOTO_ID);
			int colName 			= cusor.getColumnIndex(DatabaseHelper.PHOTO_NAME);
			int colPath 			= cusor.getColumnIndex(DatabaseHelper.PHOTO_PATH);
			int colFolderPath 		= cusor.getColumnIndex(DatabaseHelper.PHOTO_FOLDER_PATH);
			int colDate				= cusor.getColumnIndex(DatabaseHelper.PHOTO_DATE_MODIFIED);
			
				photoItem = new PhotoItemModel();
				
				photoItem.id = cusor.getInt(colID);
				photoItem.photoName = cusor.getString(colName);
				photoItem.photoAbsolutePath = cusor.getString(colPath);
				photoItem.folderPath = cusor.getString(colFolderPath);
				String strDate = cusor.getString(colDate);
				photoItem.photoDate = photoItem.stringToDate(strDate);
			cusor.close();
		}
		
		
		close();
		
		return photoItem;
	}
	
	public boolean isFavourite(String absolutePath){
		boolean ret = false;
		open();
		Cursor cusor = mDataBase.query(DatabaseHelper.FAVOURITE_TABLE,null, 
				DatabaseHelper.PHOTO_PATH +"=?", new String[]{absolutePath}, null, null, null);
		if(cusor != null && cusor.getCount()>0){
			ret = true;
			cusor.close();
		}
		close();
		return ret;
	}
	
}
