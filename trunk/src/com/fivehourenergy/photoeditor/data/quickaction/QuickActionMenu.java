package com.fivehourenergy.photoeditor.data.quickaction;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.data.model.PhotoItemModel;
import com.fivehourenergy.photoeditor.widget.quickaction.QuickActionView;
import com.fivehourenergy.photoeditor.widget.quickaction3d.QuickAction;
import com.fivehourenergy.photoeditor.widget.quickaction3d.QuickAction.OnActionItemClickListener;


public class QuickActionMenu {

	public static final int NORMAL_TYPE = 100;
	public static final int PHOTO_TYPE = 200;
	public static final int FAVOURITE_TYPE = 300;
	
	private ActionMenuItem mActionMenuSlideShow;
	private ActionMenuItem mActionMenuSortPhoto;
	private ActionMenuItem mActionMenuDesign;
	private ActionMenuItem mActionMenuAbout;
	private ActionMenuItem mActionMenuSortOrder;
	private ActionMenuItem mActionMenuSortColumn;
	private ActionMenuItem mActionMenuCopy;
	private ActionMenuItem mActionMenuCopyAll;
	private ActionMenuItem mActionMenuAddFavourite;
	private ActionMenuItem mActionMenuDelete;
	private ActionMenuItem mActionMenuMove;
	private ActionMenuItem mActionMenuPaste;
	private ActionMenuItem mActionMenuEdit;
	private ActionMenuItem mActionMenuSetPassword;
	private ActionMenuItem mActionMenuWallPaper;
	private ActionMenuItem mActionMenuShare;
	private ActionMenuItem mActionMenuRemove;
	private ActionMenuItem mActionMenuGridDate;
	private ActionMenuItem mActionMenuGridNormal;
	private ActionMenuItem mActionMenuCurver;
	private ActionMenuItem mActionMenuStaggered;
	private ActionMenuItem mActionMenuInfo;
	
	private static QuickActionMenu INSTANCE;
	
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	
	private QuickActionMenu(){
		createActionItemList();
	}
	
	public static QuickActionMenu getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new QuickActionMenu();
		}
		return INSTANCE;
	}
	
	private BaseAdapter adapter;
	public void showActionMenu(final Activity context,final View anchor,int menuType,final OnActionMenuClickListener listener){
		QuickActionView qa = QuickActionView.Builder(anchor);
		switch (menuType) {
		case NORMAL_TYPE:
			adapter = new PhotoActionAdapter(context, getListActionNormal());
			break;
		case PHOTO_TYPE:
			adapter = new PhotoActionAdapter(context, getListActionPhoto(null));
			break;
			
		case FAVOURITE_TYPE:
			adapter = new PhotoActionAdapter(context, getListActionFavourite());
			break;
		default:
			break;
		}
		qa.setNumColumns(3);
		qa.setAdapter(adapter);
		qa.setOnClickListener( new DialogInterface.OnClickListener() {

			@Override
			public void onClick( DialogInterface dialog, int which ) {
				dialog.dismiss();
				if (listener!=null) {
					listener.onActionMenuClick(anchor, (ActionMenuItem) adapter.getItem(which));
				}
				Toast.makeText( context, "Selected item: " + which, Toast.LENGTH_SHORT ).show();
			}
		} );
		
		qa.show();
		
	}
	
	public void showActionMenu3D(final ActionInput input){
		final QuickAction quickAction = new QuickAction(input.activity, input.orientation);
		ArrayList<ActionMenuItem> items = null;
		switch (input.menuType) {
		case NORMAL_TYPE:
			items = getListActionNormal();
			break;
		case PHOTO_TYPE:
			items = getListActionPhoto(input.data);
			break;
			
		case FAVOURITE_TYPE:
			items = getListActionFavourite();
			break;
		default:
			break;
		}
		
		if(items == null) return;
		for (int i = 0; i < items.size(); i++) {
			quickAction.addActionItem(items.get(i));
		}
		quickAction.setOnActionItemClickListener(new OnActionItemClickListener() {
			
			@Override
			public void onItemClick(QuickAction source, int pos, ActionMenuItem item) {
				if (input.listener!=null) {
					input.listener.onActionMenuClick(input.anchor, item);
				}
				Toast.makeText( input.activity, "Selected item: " + pos, Toast.LENGTH_SHORT ).show();
			}
		});
		
		quickAction.show(input.anchor);
	}
	
	public interface OnActionMenuClickListener{
		public void onActionMenuClick(View v,ActionMenuItem actionMenuItem);
	}
	
	public ArrayList<ActionMenuItem> getListActionNormal(){
		ArrayList<ActionMenuItem> listActionNormal = new ArrayList<ActionMenuItem>();
//		listActionNormal.add(mActionMenuSortColumn);
//		listActionNormal.add(mActionMenuAbout);
//		listActionNormal.add(mActionMenuDesign);
//		listActionNormal.add(mActionMenuSortPhoto);
		listActionNormal.add(mActionMenuSlideShow);
		listActionNormal.add(mActionMenuGridNormal);
		listActionNormal.add(mActionMenuGridDate);
		listActionNormal.add(mActionMenuCurver);
		listActionNormal.add(mActionMenuStaggered);
//		listActionNormal.add(mActionMenuCopyAll);
//		listActionNormal.add(mActionMenuPaste);
		return listActionNormal;
	}
	
	
	public ArrayList<ActionMenuItem> getListActionPhoto(PhotoItemModel model){
		ArrayList<ActionMenuItem> listActionPhoto = new ArrayList<ActionMenuItem>();
		listActionPhoto.add(mActionMenuWallPaper);
//		listActionPhoto.add(mActionMenuCopy);
		listActionPhoto.add(mActionMenuSlideShow);
//		listActionPhoto.add(mActionMenuMove);
		if(model!=null && !model.isFavourite) 
			listActionPhoto.add(mActionMenuAddFavourite);
		listActionPhoto.add(mActionMenuDelete);
		listActionPhoto.add(mActionMenuShare);
		listActionPhoto.add(mActionMenuInfo);
		listActionPhoto.add(mActionMenuEdit);
		return listActionPhoto;
	}
	
	public ArrayList<ActionMenuItem> getListActionFavourite(){
		ArrayList<ActionMenuItem> listActionFavourite = new ArrayList<ActionMenuItem>();
		listActionFavourite.add(mActionMenuWallPaper);
		listActionFavourite.add(mActionMenuEdit);
		listActionFavourite.add(mActionMenuRemove);
		listActionFavourite.add(mActionMenuShare);
		listActionFavourite.add(mActionMenuInfo);
		listActionFavourite.add(mActionMenuSlideShow);
		return listActionFavourite;
		
	}
	
	private void createActionItemList(){
		
		mActionMenuSlideShow = new ActionMenuItem();
		mActionMenuSlideShow.addAction(ActionMenu.ACTION_SLIDE_SHOW)
					.addResIcon(R.drawable.icon)
					.addText("Slide Show");
		
		mActionMenuSortPhoto = new ActionMenuItem();
		mActionMenuSortPhoto.addAction(ActionMenu.ACTION_SORT_PHOTO)
					.addResIcon(R.drawable.icon)
					.addText("Sort By Date");
		
		mActionMenuDesign = new ActionMenuItem();
		mActionMenuDesign.addAction(ActionMenu.ACTION_DESIGN)
					.addResIcon(R.drawable.icon)
					.addText("Design");
		
		mActionMenuAbout = new ActionMenuItem();
		mActionMenuAbout.addAction(ActionMenu.ACTION_VIEW_ABOUT)
					.addResIcon(R.drawable.icon)
					.addText("About");
		
		mActionMenuSortOrder = new ActionMenuItem();
		mActionMenuSortOrder.addAction(ActionMenu.ACTION_VIEW_ORDER)
					.addResIcon(R.drawable.icon)
					.addText("Order");
		
		mActionMenuSortColumn = new ActionMenuItem();
		mActionMenuSortColumn.addAction(ActionMenu.ACTION_VIEW_COLUMN)
					.addResIcon(R.drawable.icon)
					.addText("Column");
		
		mActionMenuCopy = new ActionMenuItem();
		mActionMenuCopy.addAction(ActionMenu.ACTION_COPY)
					.addResIcon(R.drawable.icon)
					.addText("Copy");
		
		mActionMenuCopyAll = new ActionMenuItem();
		mActionMenuCopyAll.addAction(ActionMenu.ACTION_COPY_ALL)
					.addResIcon(R.drawable.icon)
					.addText("Copy All");
		
		mActionMenuAddFavourite = new ActionMenuItem();
		mActionMenuAddFavourite.addAction(ActionMenu.ACTION_ADD_FAVOURITE)
					.addResIcon(R.drawable.icon)
					.addText("Add Favourite");
		
		mActionMenuDelete = new ActionMenuItem();
		mActionMenuDelete.addAction(ActionMenu.ACTION_DELETE)
					.addResIcon(R.drawable.icon)
					.addText("Delete");
		
		mActionMenuMove = new ActionMenuItem();
		mActionMenuMove.addAction(ActionMenu.ACTION_MOVE)
					.addResIcon(R.drawable.icon)
					.addText("Move Photo");
		
		mActionMenuPaste = new ActionMenuItem();
		mActionMenuPaste.addAction(ActionMenu.ACTION_PASTE)
					.addResIcon(R.drawable.icon)
					.addText("Paste Photo");
		
		mActionMenuEdit = new ActionMenuItem();
		mActionMenuEdit.addAction(ActionMenu.ACTION_EDIT_PHOTO)
					.addResIcon(R.drawable.icon)
					.addText("Edit Photo");
		
		mActionMenuSetPassword = new ActionMenuItem();
		mActionMenuSetPassword.addAction(ActionMenu.ACTION_SET_PASSWORD)
					.addResIcon(R.drawable.icon)
					.addText("Set Password");
		
		mActionMenuWallPaper = new ActionMenuItem();
		mActionMenuWallPaper.addAction(ActionMenu.ACTION_SET_WALLPAPER)
					.addResIcon(R.drawable.icon)
					.addText("Set WallPaper");
		
		mActionMenuShare = new ActionMenuItem();
		mActionMenuShare.addAction(ActionMenu.ACTION_SHARE)
					.addResIcon(R.drawable.icon)
					.addText("Share");
		
		mActionMenuRemove = new ActionMenuItem();
		mActionMenuRemove.addAction(ActionMenu.ACTION_REMOVE_FAVOURITE)
					.addResIcon(R.drawable.icon)
					.addText("Remove");
		
		mActionMenuGridNormal = new ActionMenuItem();
		mActionMenuGridNormal.addAction(ActionMenu.ACTION_VIEW_GRID_NORMAL)
					.addResIcon(R.drawable.icon)
					.addText("Grid");

		mActionMenuGridDate = new ActionMenuItem();
		mActionMenuGridDate.addAction(ActionMenu.ACTION_VIEW_GRID_DATE)
					.addResIcon(R.drawable.icon)
					.addText("Grid Date");
		
		mActionMenuCurver = new ActionMenuItem();
		mActionMenuCurver.addAction(ActionMenu.ACTION_VIEW_CURVER)
					.addResIcon(R.drawable.icon)
					.addText("Curver");
		
		mActionMenuStaggered = new ActionMenuItem();
		mActionMenuStaggered.addAction(ActionMenu.ACTION_VIEW_STAGGERED)
					.addResIcon(R.drawable.icon)
					.addText("Staggered");
		
		mActionMenuInfo = new ActionMenuItem();
		mActionMenuInfo.addAction(ActionMenu.ACTION_VIEW_INFO)
					.addResIcon(R.drawable.icon)
					.addText("Photo Info");
		
	}
	
	public enum ActionMenu{
		ACTION_CAMERA,
		ACTION_NEW_FOLDER,
		ACTION_SORT_PHOTO,
		ACTION_ADD_FAVOURITE,
		ACTION_UPDATE_FAVOURITE,
		ACTION_REMOVE_FAVOURITE,
		ACTION_DELETE,
		ACTION_COPY,
		ACTION_COPY_ALL,
		ACTION_MOVE,
		ACTION_PASTE,
		ACTION_EDIT_PHOTO,
		ACTION_SET_PASSWORD,
		ACTION_EDIT_FOLDER,
		ACTION_DESIGN,
		ACTION_GRID_STYLE,
		ACTION_VIEW_ABOUT,
		ACTION_SLIDE_SHOW,
		ACTION_VIEW_ORDER,
		ACTION_VIEW_COLUMN,
		ACTION_VIEW_GRID_NORMAL,
		ACTION_VIEW_GRID_DATE,
		ACTION_VIEW_STAGGERED,
		ACTION_VIEW_CURVER,
		ACTION_VIEW_INFO,
		ACTION_SHARE,
		ACTION_SET_WALLPAPER,
		ACTION_RELOAD,
		ACTION_AUTO_SLIDE
	}
	
}
