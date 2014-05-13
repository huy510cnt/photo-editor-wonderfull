package com.fivehourenergy.photoeditor.ui;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.data.DataController;
import com.fivehourenergy.photoeditor.data.database.DatabaseController;
import com.fivehourenergy.photoeditor.data.model.PhotoItemModel;
import com.fivehourenergy.photoeditor.data.quickaction.ActionInput;
import com.fivehourenergy.photoeditor.data.quickaction.ActionMenuExecutor;
import com.fivehourenergy.photoeditor.data.quickaction.ActionMenuItem;
import com.fivehourenergy.photoeditor.data.quickaction.QuickActionMenu;
import com.fivehourenergy.photoeditor.data.quickaction.QuickActionMenu.OnActionMenuClickListener;
import com.fivehourenergy.photoeditor.ui.PhotoLibraryAdapter.GridViewType;
import com.fivehourenergy.photoeditor.ui.base.BasePhotoFragment;
import com.fivehourenergy.photoeditor.util.UniversalImageLoader;
import com.fivehourenergy.photoeditor.widget.quickaction3d.QuickAction;
import com.fivehourenergy.photoeditor.widget.staggeredgrid.StaggeredGridView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

// TODO: Auto-generated Javadoc
/**
 * The Class PhotoLibraryFragment.
 */
public class PhotoLibraryFragment extends BasePhotoFragment{

	/** The m grid view. */
	private GridView mGridView;
	
	private StaggeredGridView mStaggeredGridView;
	
	/** The m adapter. */
	private PhotoLibraryAdapter mAdapter;
	
	/** The m datas. */
	private ArrayList<PhotoItemModel> mDatas;
	
	/* (non-Javadoc)
	 * @see com.fivehourenergy.photoeditor.ui.base.BaseFragment#onCreateContentView(android.view.LayoutInflater, android.view.ViewGroup)
	 */
	@Override
	protected View onCreateContentView(LayoutInflater inflater,
			ViewGroup container) {
		View v = inflater.inflate(R.layout.photo_library_screen, null);
		initGridView(v);
		initStaggeredGridView(v);
		showPhotos();
		return v;
	}
	
	private void initGridView(View parent){
		mGridView = (GridView) parent.findViewById(R.id.grid_view);
		mGridView.setNumColumns(2);
		mGridView.setOnScrollListener(new PauseOnScrollListener(UniversalImageLoader.getInstance(), false, true));
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int pos,
					long id) {
//				getMainActiviy().openAviaryEditor(mDatas.get(pos).photoAbsolutePath);
				int type;
				if(getMainActiviy().currentScreenPos == LeftMenuFragment.FAVOURITE_POS){
					type = QuickActionMenu.FAVOURITE_TYPE;
				}else{
					type = QuickActionMenu.PHOTO_TYPE;
				}
				final ActionInput input = new ActionInput();
				input.activity = getActivity();
				input.anchor = v;
				input.menuType = type;
				input.orientation = QuickAction.HORIZONTAL;
				input.data = mAdapter.getItem(pos);
				input.data.isFavourite = DatabaseController.getInstanceOfDataSource().isFavourite(mAdapter.getItem(pos).photoAbsolutePath);
				input.listener = new OnActionItemClick(mAdapter.getItem(pos),pos);
				QuickActionMenu.getInstance().showActionMenu3D(input);
			}
		});
	}
	
	private void initStaggeredGridView(View parent){
		mStaggeredGridView = (StaggeredGridView) parent.findViewById(R.id.staggered_grid);
		mStaggeredGridView.setColumnCount(2);
		mStaggeredGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
	}

	/**
	 * Show photos.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void showPhotos(){
		mDatas = DataController.getInstance().getCurrentListPhoto();
		if(mDatas == null) return;
		GridViewType type = DataController.getInstance().getCurrentGridType();
		mAdapter = new PhotoLibraryAdapter(getActivity(), mDatas,type);
		switch (type) {
		case NORMAL:
			mGridView.setVisibility(View.VISIBLE);
			mGridView.setNumColumns(3);
			mGridView.setAdapter(mAdapter);
			mStaggeredGridView.setVisibility(View.GONE);
			break;
		case IMAGE_CURVER:
			mGridView.setVisibility(View.VISIBLE);
			mGridView.setNumColumns(3);
			mGridView.setAdapter(mAdapter);
			mStaggeredGridView.setVisibility(View.GONE);
			break;
			
		case IMAGE_WITH_DATE:
			mGridView.setVisibility(View.VISIBLE);
			mGridView.setNumColumns(2);
			mGridView.setAdapter(mAdapter);
			mStaggeredGridView.setVisibility(View.GONE);
			break;
		case STAGGED_LIST:
			mStaggeredGridView.setVisibility(View.VISIBLE);
			mStaggeredGridView.setColumnCount(2);
			mStaggeredGridView.setAdapter(mAdapter);
			mGridView.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
	

	/* (non-Javadoc)
	 * @see com.fivehourenergy.photoeditor.data.PhotoEditorObserver#update()
	 */
	@Override
	public void update() {
		if(getMainActiviy().currentScreenPos == LeftMenuFragment.FAVOURITE_POS){
			DataController.getInstance().setCurrentListPhoto(DatabaseController.getInstanceOfDataSource().getListFavourite());
		}else{
			DataController.getInstance().reloadCurrentList();
		}
		showPhotos();
	}
	
	/**
	 * The Class OnActionItemClick.
	 */
	public class OnActionItemClick implements OnActionMenuClickListener{

		/** The photo item. */
		public PhotoItemModel photoItem;
		public int mIndex;;
		
		/**
		 * Instantiates a new on action item click.
		 *
		 * @param model the model
		 */
		public OnActionItemClick(PhotoItemModel model,int index){
			photoItem = model;
			mIndex = index;
		}

		@Override
		public void onActionMenuClick(View v, ActionMenuItem actionMenuItem) {
			switch (actionMenuItem.getAction()) {
			case ACTION_ADD_FAVOURITE:
				if (photoItem!=null){
					DatabaseController.getInstanceOfDataSource().insertFavourite(photoItem);
				}
				break;
			case ACTION_COPY:
				ActionMenuExecutor.executeActionCopyPhoto(photoItem.photoAbsolutePath);
				break;
			case ACTION_SLIDE_SHOW:
				SlideShowFragment slideFrag = new SlideShowFragment();
				slideFrag.mCurrentIndex = mIndex;
				getMainActiviy().switchContent(slideFrag, true);
				getMainActiviy().disableSlideMenu();
				break;
			case ACTION_EDIT_PHOTO:
				getMainActiviy().openAviaryEditor(photoItem.photoAbsolutePath);
				break;
			case ACTION_DELETE:
				ActionMenuExecutor.executeActionDeletePhoto(PhotoLibraryFragment.this, photoItem.photoAbsolutePath);
				break;
			case ACTION_REMOVE_FAVOURITE:
				ActionMenuExecutor.executeActionRemoveFavourite(PhotoLibraryFragment.this,photoItem.photoAbsolutePath);
				break;
			case ACTION_VIEW_INFO:
				ActionMenuExecutor.executeActionPhotoInfo(PhotoLibraryFragment.this,photoItem.photoAbsolutePath);
				break;
			case ACTION_SHARE:
				ActionMenuExecutor.executeActionShare(getActivity(), photoItem.photoAbsolutePath);
				break;
			case ACTION_SET_WALLPAPER:
				ActionMenuExecutor.executeActionSetWallPaper(getActivity(),photoItem.photoAbsolutePath);
				break;
			default:
				break;
			}
		}
		
		/* (non-Javadoc)
		 * @see com.fivehourenergy.photoeditor.ui.base.PhotoEditorQuickAction.OnActionItemClickListener#onActionItemClick(android.view.View, com.fivehourenergy.photoeditor.ui.base.PhotoEditorQuickAction.PhotoAction)
		 */
		
	}
	
	@Override
	protected String getTitle() {
		return null;
	}
}
