package com.fivehourenergy.photoeditor.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.activity.MainActivity;
import com.fivehourenergy.photoeditor.data.DataController;
import com.fivehourenergy.photoeditor.data.database.DatabaseController;
import com.fivehourenergy.photoeditor.data.model.PhotoItemModel;
import com.fivehourenergy.photoeditor.data.quickaction.ActionMenuExecutor;
import com.fivehourenergy.photoeditor.ui.PhotoLibraryAdapter.GridViewType;
import com.fivehourenergy.photoeditor.ui.base.BasePhotoFragment;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader.IDisplayImageOption;
import com.fivehourenergy.photoeditor.widget.ExtendedViewPager;
import com.fivehourenergy.photoeditor.widget.TouchImageView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

@SuppressLint("ValidFragment")
public class SlideShowFragment extends BasePhotoFragment implements OnClickListener{

	public ExtendedViewPager pager;
	public ViewGroup vBottomBar;
	public ArrayList<PhotoItemModel> mDatas;
	private Gallery mGallery;
	
	@SuppressLint("ValidFragment")
	public int mCurrentIndex;
	
	@Override
	protected View onCreateContentView(LayoutInflater inflater,
			ViewGroup container) {
		View v  = inflater.inflate(R.layout.slide_show_screen, null);
		vBottomBar = (ViewGroup) v.findViewById(R.id.llBottomBar);
		vBottomBar.setOnClickListener(this);
		vBottomBar.findViewById(R.id.img_add_favourite).setOnClickListener(this);
		vBottomBar.findViewById(R.id.img_setwallpaper).setOnClickListener(this);
		vBottomBar.findViewById(R.id.img_share).setOnClickListener(this);
		vBottomBar.findViewById(R.id.img_edit).setOnClickListener(this);
		vBottomBar.findViewById(R.id.img_info).setOnClickListener(this);
		
		final GestureDetector tapGestureDetector = new GestureDetector(getActivity(), new TapGestureListener());
		pager = (ExtendedViewPager) v.findViewById(R.id.pager);
		pager.setOnTouchListener(new OnTouchListener() {
	        public boolean onTouch(View v, MotionEvent event) {
	        	tapGestureDetector.onTouchEvent(event);
	            return false;
	        }
		});
		initGallery(v);
		show();
		return v;
	}

	private void initGallery(View parent){
		mGallery = (Gallery) parent.findViewById(R.id.horizontal_listview);
//		mHorizontalListView.getLayoutParams().height = MainActivity.SCREEN_WIDTH/5;
		mGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				pager.setCurrentItem(pos);
			}
		});
	}
	public void hideMenu(){
		if (vBottomBar.getVisibility() == View.VISIBLE) {
			vBottomBar.setVisibility(View.INVISIBLE);
		}
		if (mGallery.getVisibility() == View.VISIBLE) {
			mGallery.setVisibility(View.INVISIBLE);
		}
	}
	
	public void showOrHideMenu(){
		if (vBottomBar.getVisibility() == View.VISIBLE) {
			vBottomBar.setVisibility(View.INVISIBLE);
		}else{
			vBottomBar.setVisibility(View.VISIBLE);
		}
		if (mGallery.getVisibility() == View.VISIBLE) {
			mGallery.setVisibility(View.INVISIBLE);
		}else{
			mGallery.setVisibility(View.VISIBLE);
		}
	}
	
	private void show(){
		mDatas = DataController.getInstance().getCurrentListPhoto();
		if(mDatas!= null){
			mGallery.setAdapter(new PhotoLibraryAdapter(getActivity(), mDatas, GridViewType.GALLERY));
			pager.setAdapter(new ImagePagerAdapter(getActivity(),mDatas));
//			pager.setTransitionEffect(TransitionEffect.Accordion);
			pager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int pos) {
					mGallery.setSelection(pager.getCurrentItem());
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					
				}
			});
			pager.setCurrentItem(mCurrentIndex);
//			mHorizontalListView.setSelection(pager.getCurrentItem());
			Log.d("PagerActivity", "Data Size : "+mDatas.size()+"  Index :"+mCurrentIndex);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		((MainActivity)getActivity()).enableSlideMenu();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_add_favourite:
			DatabaseController.getInstanceOfDataSource().insertFavourite(mDatas.get(pager.getCurrentItem()));
//			hideMenu();
			break;
		case R.id.img_setwallpaper:
			
			break;
		case R.id.img_share:
			
			break;
		case R.id.img_edit:
			getMainActiviy().openAviaryEditor(mDatas.get(pager.getCurrentItem()).photoAbsolutePath);
//			hideMenu();
			break;
		case R.id.img_info:
			ActionMenuExecutor.executeActionPhotoInfo(SlideShowFragment.this,mDatas.get(pager.getCurrentItem()).photoAbsolutePath);
//			hideMenu();
			break;
		default:
			break;
		}
	}
	
	
	class TapGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
        	showOrHideMenu();
			return true;
        }
    }
	
	private class ImagePagerAdapter extends PagerAdapter {

		private ArrayList<PhotoItemModel> mListDatas;
		private Activity mActivity;
		
		public ImagePagerAdapter(Activity activity,ArrayList<PhotoItemModel> datas) {
			mActivity = activity;
			mListDatas = datas;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			if(mListDatas!=null){
				return mListDatas.size();
			}
			return 0;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = LayoutInflater.from(mActivity).inflate(R.layout.slide_show_item, null);
			TouchImageView imageView = (TouchImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			UniversalImageLoader.getInstance().displayImage("file:///"+mListDatas.get(position).photoAbsolutePath, imageView, IDisplayImageOption.pagerOption, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
				}
			});

			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
//			return view.equals(object);
			return view == ((View) object);
		}

//		@Override
//		public void restoreState(Parcelable state, ClassLoader loader) {
//		}
//
		@Override
		public Parcelable saveState() {
			return null;
		}
//
//		@Override
//		public void startUpdate(View container) {
//		}
	}

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

}
