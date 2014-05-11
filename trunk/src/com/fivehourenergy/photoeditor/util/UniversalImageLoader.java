package com.fivehourenergy.photoeditor.util;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.fivehourenergy.photoeditor.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

// TODO: Auto-generated Javadoc
/**
 * The Class UniversalImageLoader.
 * @author HuanND
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class UniversalImageLoader extends ImageLoader{

	/** The instance. */
	private volatile static UniversalImageLoader INSTANCE = null;
	
	/**
	 * Instantiates a new universal image loader.
	 */
	protected UniversalImageLoader() {
	}
	
	/**
	 * Gets the single instance of UniversalImageLoader.
	 *
	 * @return single instance of UniversalImageLoader
	 */
	public static UniversalImageLoader getInstance(){
		if (INSTANCE == null) {
			synchronized (UniversalImageLoader.class) {
				if (INSTANCE == null) {
					INSTANCE = new UniversalImageLoader();
				}
			}
		}
		return INSTANCE;
	}
	
	/**
	 * Load image view center crop.
	 *
	 * @param v the v
	 * @param imageUri the image uri
	 * @param regWidth the reg width
	 * @param regHeight the reg height
	 */
	public void loadImageViewCenterCrop(final View v,String imageUri, final int regWidth, final int regHeight){
		loadImageView(v, imageUri, regWidth, regHeight, IDisplayImageOption.pagerOption, BitmapType.BITMAP_CENTER_CROP, null);
	}
	
	/**
	 * Load image view.
	 *
	 * @param v the v
	 * @param imageUri the image uri
	 * @param regWidth the reg width
	 */
	public void loadImageView(final View v,String imageUri, final int regWidth){
		loadImageView(v, imageUri, regWidth, 0, IDisplayImageOption.defaultOption, BitmapType.BITMAP_DEFAULT, null);
	}
	
	/**
	 * Load image view.
	 *
	 * @param v the v
	 * @param imageUri the image uri
	 * @param regWidth the reg width
	 * @param regHeight the reg height
	 */
	public void loadImageView(final View v,String imageUri, final int regWidth, final int regHeight){
		loadImageView(v, imageUri, regWidth, regHeight, IDisplayImageOption.defaultOption, BitmapType.BITMAP_DEFAULT, null);
	}
	
	/**
	 * Load image view.
	 *
	 * @param v the v
	 * @param imageUri the image uri
	 * @param regWidth the reg width
	 * @param regHeight the reg height
	 * @param option the option
	 */
	public void loadImageView(final View v,String imageUri, final int regWidth, final int regHeight,DisplayImageOptions option){
		loadImageView(v, imageUri, regWidth, regHeight, option, BitmapType.BITMAP_DEFAULT, null);
	}
	
	/**
	 * Load image view.
	 *
	 * @param v the v
	 * @param imageUri the image uri
	 * @param regWidth the reg width
	 * @param regHeight the reg height
	 * @param option the option
	 * @param listener the listener
	 */
	public void loadImageView(final View v,String imageUri, final int regWidth, final int regHeight,DisplayImageOptions option,ImageLoadingListener listener){
		loadImageView(v, imageUri, regWidth, regHeight, option, BitmapType.BITMAP_DEFAULT, listener);
	}
	
	/**
	 * Load image view.
	 *
	 * @param v the v
	 * @param imageUri the image uri
	 * @param regWidth the reg width
	 * @param regHeight the reg height
	 * @param options the options
	 * @param type the type
	 * @param listener the listener
	 */
	protected void loadImageView(final View v,String imageUri, final int regWidth, final int regHeight,
											DisplayImageOptions options,final BitmapType type,
											ImageLoadingListener listener) {
		loadImage(imageUri, convertToImageSize(regWidth, regHeight), options == null ? IDisplayImageOption.defaultOption:options, 
				listener != null ? listener:new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				switch (type) {
				case BITMAP_DEFAULT:
					setBackgroundBitmap(v, loadedImage);
					break;
				case BITMAP_CENTER_CROP:
					setBackgroundBitmap(v, getCenterCropBitmap(loadedImage, regWidth, regHeight));
					break;
				default:
					break;
				}
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				
			}
		});
	}

	
	/**
	 * Load image.
	 *
	 * @param v the v
	 * @param uri the uri
	 */
	public void loadImage(final View v,String uri){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		loadImage(uri, options,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if(v!=null){
					setBackgroundBitmap(v, loadedImage);
				}
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				
			}
		});
	}
	/**
	 * Convert to image size.
	 *
	 * @param width the width
	 * @param height the height
	 * @return the image size
	 */
	protected ImageSize convertToImageSize(int width, int height) {
		ImageSize size = new ImageSize(width, height);
		return size;
	}

	/**
	 * Convert to image size.
	 *
	 * @param width the width
	 * @return the image size
	 */
	protected ImageSize convertToImageSize(int width) {
		return convertToImageSize(width, 0);
	}

	/**
	 * Gets the center crop bitmap.
	 *
	 * @param bitmap the bitmap
	 * @param regWidth the reg width
	 * @param regHeight the reg height
	 * @return the center crop bitmap
	 */
	protected Bitmap getCenterCropBitmap(final Bitmap bitmap,int regWidth,int regHeight){
		Bitmap ret = bitmap;
		if (ret != null && ret.getHeight() > 0 && ret.getWidth() > 0 && regWidth >0 && regHeight > 0) {
            int oriWidth = ret.getWidth();
            int oriHeight = ret.getHeight();
            float widthRatio = oriWidth / (float) regWidth;
            int meaningOriHeight = (int) (regHeight * widthRatio);
            Rect oriRec = new Rect(0, 0, oriWidth, meaningOriHeight);
            Rect targetRec = new Rect(0, 0, regWidth, regHeight); 
            if (oriHeight < meaningOriHeight) {
                oriRec.bottom = oriHeight;
                targetRec.bottom = (int) (oriHeight / widthRatio);
            }
            Bitmap returnBM = Bitmap.createBitmap(regWidth, regHeight, Config.ARGB_8888);
            Canvas canvas = new Canvas(returnBM);
            canvas.drawBitmap(ret, oriRec, targetRec, null);
            ret.recycle();
            ret = null;
            ret = returnBM;
            returnBM = null;
            canvas = null;
        }
		return ret;
	}
	
	/**
	 * Sets the background bitmap.
	 *
	 * @param v the v
	 * @param bitmap the bitmap
	 */
	protected void setBackgroundBitmap(View v,Bitmap bitmap){
		if(v instanceof ImageView){
			((ImageView)v).setImageBitmap(bitmap);
			return;
		}
		Drawable d = new BitmapDrawable(bitmap);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(d);
        } else {
            v.setBackgroundDrawable(d);
        }
	}
	
	/**
	 * The Enum BitmapType.
	 */
	public enum BitmapType{
		
		/** The bitmap default. */
		BITMAP_DEFAULT,
		
		/** The bitmap center crop. */
		BITMAP_CENTER_CROP;
	}
	
	/**
	 * The Interface IDisplayImageOption.
	 */
	public interface IDisplayImageOption {
		
		/** The default option. */
		DisplayImageOptions defaultOption 	= new DisplayImageOptions.Builder()
												.cacheInMemory(true)
												.cacheOnDisc(false)
//												.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
												.bitmapConfig(Bitmap.Config.RGB_565)
												.considerExifParams(true)
												.showStubImage(R.drawable.ic_stub)
												.showImageForEmptyUri(R.drawable.ic_empty)
												.showImageOnFail(R.drawable.ic_error)
												.build();
		
		DisplayImageOptions circleOption 	 = new DisplayImageOptions.Builder()
												.cacheInMemory(true)
												.cacheOnDisc(false)
//		.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
												.bitmapConfig(Bitmap.Config.RGB_565)
												.considerExifParams(true)
												.showStubImage(R.drawable.ic_stub)
												.showImageForEmptyUri(R.drawable.ic_empty)
												.showImageOnFail(R.drawable.ic_error)
												.displayer(new RoundedBitmapDisplayer(1000))
												.build();

		/** The animation option. */
		DisplayImageOptions animationOption = new DisplayImageOptions.Builder()
												.cacheInMemory(true)
												.cacheOnDisc(false)
												.considerExifParams(true)
												.bitmapConfig(Bitmap.Config.RGB_565)
												.build();

		/** The pager option. */
		DisplayImageOptions pagerOption 	= new DisplayImageOptions.Builder()
												.cacheInMemory(true)
												.cacheOnDisc(false)
												.showImageForEmptyUri(R.drawable.ic_empty)
												.showImageOnFail(R.drawable.ic_error)
												.considerExifParams(true)
//												.resetViewBeforeLoading()
//												.cacheOnDisc()
												.imageScaleType(ImageScaleType.EXACTLY)
												.bitmapConfig(Bitmap.Config.RGB_565)
												.displayer(new FadeInBitmapDisplayer(300))
												.build();
	}

}
