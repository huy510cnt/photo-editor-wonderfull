package com.fivehourenergy.photoeditor.ui;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * The Class PhotoLibraryAdapter.
 */
public class PhotoLibraryAdapter extends BaseListAdapter<PhotoItemModel>{

	private GridViewType mGridType;
	private Fragment mFrag;
	
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
	
	public PhotoLibraryAdapter(Activity activity,Fragment frag,
			ArrayList<PhotoItemModel> datas,GridViewType type) {
		super(activity, datas);
		this.mGridType = type;
		mFrag = frag;
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
		case GALLERY:
			return getViewGallery(position, convertView, parent);
		default:
			return getViewNormal(position, convertView, parent);
		}
	}

	private View getViewNormal(final int position, View convertView, ViewGroup parent){
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from(mActivity).inflate(R.layout.photo_item, null);
		}
		ImageView imageView = ViewHolder.get(row, R.id.imgPhotoItem);
		ImageView imageMore = ViewHolder.get(row, R.id.img_more);
		int size = BaseSlidingFragmentActivity.SCREEN_WIDTH/3;
		imageView.getLayoutParams().width = size;
		imageView.getLayoutParams().height = size;
		
//		UniversalImageLoader.getInstance().loadImageView(imageView,"file:///"+getItem(position).photoAbsolutePath, size,size);
		UniversalImageLoader.getInstance().displayImage("file:///"+getItem(position).photoAbsolutePath, imageView, IDisplayImageOption.defaultOption);
		imageMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mFrag!=null && mFrag instanceof PhotoLibraryFragment){
					((PhotoLibraryFragment)mFrag).showPopupAtPosition(arg0, position);
				}
			}
		});

		return row;
	}
	
	private View getViewWithDate(final int position, View convertView, ViewGroup parent){
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from(mActivity).inflate(R.layout.photo_item_with_date, null);
		}
		ImageView imageMore = ViewHolder.get(row, R.id.img_more);
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
		imageMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mFrag!=null && mFrag instanceof PhotoLibraryFragment){
					((PhotoLibraryFragment)mFrag).showPopupAtPosition(arg0, position);
				}
			}
		});


		return row;
	}
	
	private View getViewCurver(final int position, View convertView, ViewGroup parent){
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from(mActivity).inflate(R.layout.photo_item_curver, null);
		}
		ImageView imageMore = ViewHolder.get(row, R.id.img_more);
		ImageView imageView = ViewHolder.get(row, R.id.imgPhotoItem);
		int size = BaseSlidingFragmentActivity.SCREEN_WIDTH/3;
		imageView.getLayoutParams().width = size;
		imageView.getLayoutParams().height = size;
		
//		UniversalImageLoader.getInstance().loadImageView(imageView,"file:///"+getItem(position).photoAbsolutePath, size,size);
		UniversalImageLoader.getInstance().displayImage("file:///"+getItem(position).photoAbsolutePath, imageView, curverNormalOption);
		imageMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mFrag!=null && mFrag instanceof PhotoLibraryFragment){
					((PhotoLibraryFragment)mFrag).showPopupAtPosition(arg0, position);
				}
			}
		});
		return row;
	}
	
	private View getViewGallery(int position, View convertView, ViewGroup parent){
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from(mActivity).inflate(R.layout.photo_item, null);
			row.findViewById(R.id.img_more).setVisibility(View.GONE);
		}
		ImageView imageView = ViewHolder.get(row, R.id.imgPhotoItem);
		int size = BaseSlidingFragmentActivity.SCREEN_WIDTH/5;
		imageView.getLayoutParams().width = size;
		imageView.getLayoutParams().height = size;
		
//		UniversalImageLoader.getInstance().loadImageView(imageView,"file:///"+getItem(position).photoAbsolutePath, size,size);
		UniversalImageLoader.getInstance().displayImage("file:///"+getItem(position).photoAbsolutePath, imageView, IDisplayImageOption.curverOption);

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
    
    /** The default option. */
	DisplayImageOptions curverNormalOption 	= new DisplayImageOptions.Builder()
											.cacheInMemory(true)
											.cacheOnDisc(false)
//											.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
											.bitmapConfig(Bitmap.Config.RGB_565)
											.considerExifParams(true)
											.showStubImage(R.drawable.ic_stub)
											.showImageForEmptyUri(R.drawable.ic_empty)
											.showImageOnFail(R.drawable.ic_error)
											.displayer(new RoundedBitmapDisplayer(mActivity.getResources().getDimensionPixelSize(R.dimen.item_curver_normal)))
											.build();
	
	public enum GridViewType{
		NORMAL,
		IMAGE_WITH_DATE,
		IMAGE_CURVER,
		STAGGED_LIST,
		GALLERY
	}
}
