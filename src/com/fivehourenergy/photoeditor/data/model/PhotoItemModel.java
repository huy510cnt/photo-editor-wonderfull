package com.fivehourenergy.photoeditor.data.model;

import java.text.ParseException;
import java.util.Date;

import android.text.format.DateFormat;


// TODO: Auto-generated Javadoc
/**
 * The Class PhotoItemModel.
 */
public class PhotoItemModel {

	/** The id. */
	public int id;
	
	/** The photo name. */
	public String photoName = "";
	
	/** The photo absolute path. */
	public String photoAbsolutePath = "";
	
	/** The folder path. */
	public String folderPath = "";
	
	/** The photo date. */
	public Date photoDate;
	
	/** The photo size. */
	public int photoSize = 0;
	
	public boolean isFavourite = false;
	
	public String getDateString(){
		if (photoDate == null) return "";
		java.text.Format formatter = new java.text.SimpleDateFormat("yyyy/MM/dd");
		String s = formatter.format(photoDate);
		return s;
	}
	
	public Date stringToDate(String strDate){
		java.text.DateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	public String getDayOfWeek(){
		if (photoDate==null) {
			return "";
		}
		return (String) DateFormat.format("EEEE", photoDate);
	}
	
	public String getDayOfMonth(){
		if (photoDate==null) {
			return "";
		}
		return (String) DateFormat.format("dd", photoDate);
	}
	
	public String getMonthOfYear(){
		if (photoDate==null) {
			return "";
		}
		return (String) DateFormat.format("MMM", photoDate);
	}
	
	public String getYear(){
		if (photoDate==null) {
			return "";
		}
		return (String) DateFormat.format("yyyy", photoDate);
	}
}
