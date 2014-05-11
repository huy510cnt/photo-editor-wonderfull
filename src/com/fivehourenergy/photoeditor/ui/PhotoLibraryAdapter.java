package com.fivehourenergy.photoeditor.ui;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.activity.BaseSlidingFragmentActivity;
import com.fivehourenergy.photoeditor.data.BaseListAdapter;
import com.fivehourenergy.photoeditor.data.model.PhotoItemModel;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader.IDisplayImageOption;
import com.fivehourenergy.photoeditor.widget.ViewHolder;
import com.fivehourenergy.photoeditor.widget.staggeredgrid.util.DynamicHeightImageView;

/**
 * The Class PhotoLibraryAdapter.
 */
public class PhotoLibraryAdapter extends BaseListAdapter<PhotoItemModel>{

	private GridViewType mGridType;
	
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	private Random mRandom = new Random();
	/**
	 * Instantiates a new photo library adapter.
	 *
	 * @param activity the activity
	 * @param datas the datas
	 */
	public PhotoLibraryAdapter(Activity activity,
			ArrayList<PhotoItemModel> datas,GridViewType type) {
		super(activity, datas);
		this.mGridType = type;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch (mGridType) {
		case NORMAL:
			return getViewNormal(position, convertView, parent);
		case IMAGE_WITH_DATE:
			return getViewWithDate(position, convertView, parent);
		case IMAGE_CURVER:
			return getViewCurver(position, convertView, parent);
		case STAGGED_LIST:
			return getViewStaggeredGrid(position, convertView, parent);
		case GALLERY:
			return getViewGallery(position, convertView, parent);
		default:
			return getViewNormal(position, convertView, parent);
		}
	}

	private View getViewNormal(int position, View convertView, ViewGroup parent){
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from(mActivity).inflate(R.layout.photo_item, null);
		}
		ImageView imageView = ViewHolder.get(row, R.id.imgPhotoItem);
		int size = BaseSlidingFragmentActivity.SCREEN_WIDTH/3;
		imageView.getLayoutParams().width = size;
		imageView.getLayoutParams().height = size;
		
//		UniversalImageLoader.getInstance().loadImageView(imageView,"file:///"+getItem(position).photoAbsolutePath, size,size);
		UniversalImageLoader.getInstance().displayImage("file:///"+getItem(position).photoAbsolutePath, imageView, IDisplayImageOption.defaultOption);

		return row;
	}
	
	private View getViewWithDate(int position, View convertView, ViewGroup parent){
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from(mActivity).inflate(R.layout.photo_item_with_date, null);
		}
		ImageView imageView = ViewHolder.get(row, R.id.imgPhotoItem);
		TextView tvDayOfMonth = ViewHolder.get(row, R.id.tvDayOfMonth);
		TextView tvMonthYear = ViewHolder.get(row, R.id.tvMonthYear);
		tvDayOfMonth.setText(getItem(position).getDayOfMonth());
		tvMonthYear.setText(getItem(position).getMonthOfYear()+","+getItem(position).getYear());
		
		int size = BaseSlidingFragmentActivity.SCREEN_WIDTH/2;
		imageView.getLayoutParams().width = size;
		imageView.getLayoutParams().height = size;
		
//		UniversalImageLoader.getInstance().loadImageView(imageView,"file:///"+getItem(position).photoAbsolutePath, size,size);
		UniversalImageLoader.getInstance().displayImage("file:///"+getItem(position).photoAbsolutePath, imageView, IDisplayImageOption.defaultOption);

		return row;
	}
	
	private View getViewCurver(int position, View convertView, ViewGroup parent){
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from(mActivity).inflate(R.layout.photo_item, null);
		}
		ImageView imageView = ViewHolder.get(row, R.id.imgPhotoItem);
		int size = BaseSlidingFragmentActivity.SCREEN_WIDTH/3;
		imageView.getLayoutParams().width = size;
		imageView.getLayoutParams().height = size;
		
//		UniversalImageLoader.getInstance().loadImageView(imageView,"file:///"+getItem(position).photoAbsolutePath, size,size);
		UniversalImageLoader.getInstance().displayImage("file:///"+getItem(position).photoAbsolutePath, imageView, IDisplayImageOption.defaultOption);

		return row;
	}
	
	private View getViewStaggeredGrid(int position, View convertView, ViewGroup parent){
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from(mActivity).inflate(R.layout.staggered_grid_item, null);
		}
		DynamicHeightImageView imageView = ViewHolder.get(row, R.id.img_staggered);
//		int size = BaseSlidingFragmentActivity.SCREEN_WIDTH/3;
//		imageView.getLayoutParams().width = size;
//		imageView.getLayoutParams().height = size;
		imageView.setHeightRatio(getPositionRatio(position));
		
//		UniversalImageLoader.getInstance().loadImageView(imageView,"file:///"+getItem(position).photoAbsolutePath, size,size);
		UniversalImageLoader.getInstance().displayImage("file:///"+getItem(position).photoAbsolutePath, imageView, IDisplayImageOption.defaultOption);

		return row;
	}
	
	private View getViewGallery(int position, View convertView, ViewGroup parent){
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from(mActivity).inflate(R.layout.photo_item, null);
		}
		ImageView imageView = ViewHolder.get(row, R.id.imgPhotoItem);
		int size = BaseSlidingFragmentActivity.SCREEN_WIDTH/5;
		imageView.getLayoutParams().width = size;
		imageView.getLayoutParams().height = size;
		
//		UniversalImageLoader.getInstance().loadImageView(imageView,"file:///"+getItem(position).photoAbsolutePath, size,size);
		UniversalImageLoader.getInstance().displayImage("file:///"+getItem(position).photoAbsolutePath, imageView, IDisplayImageOption.defaultOption);

		return row;
	}
		
	private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
    
	public enum GridViewType{
		NORMAL,
		IMAGE_WITH_DATE,
		IMAGE_CURVER,
		STAGGED_LIST,
		GALLERY
	}
}
