package com.fivehourenergy.photoeditor.ui.base;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.widget.quickaction.ActionItemText;
import com.fivehourenergy.photoeditor.widget.quickaction.QuickActionView;

public class PhotoEditorQuickAction {

	public static void showAction(final Activity context,final View v,final OnActionItemClickListener listener) {
		// create the quick action view, passing the view anchor
		QuickActionView qa = QuickActionView.Builder(v);

		// set the adapter
		qa.setAdapter(new CustomAdapter(context));

		// set the number of columns ( setting -1 for auto )
		qa.setNumColumns((int) (2 + (Math.random() * 10)));
		qa.setOnClickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(listener!=null){
					listener.onActionItemClick(v, PhotoAction.ACTION_ADD_FAVOURITE);
				}
				Toast.makeText(context, "Selected item: " + which,
						Toast.LENGTH_SHORT).show();
			}
		});

		// finally show the view
		qa.show();
	}
	
	public interface OnActionItemClickListener{
		public void onActionItemClick(View v,PhotoAction action);
	}
	
	public enum PhotoAction{
		ACTION_ADD_FAVOURITE,
		ACTION_REMOVE_FAVOURITE,
		ACTION_EDIT_PHOTO,
		ACTION_DELETE,
		ACTION_COPY,
		ACTION_PASTE,
		ACTION_SHOW
	}

	static class CustomAdapter extends BaseAdapter {

		static final int[] ICONS = new int[] {

		android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_agenda,
				android.R.drawable.ic_menu_always_landscape_portrait,
				android.R.drawable.ic_menu_call,
				android.R.drawable.ic_menu_camera,
				android.R.drawable.ic_menu_close_clear_cancel,
				android.R.drawable.ic_menu_compass,
				android.R.drawable.ic_menu_crop,
				android.R.drawable.ic_menu_day,
				android.R.drawable.ic_menu_delete,
				android.R.drawable.ic_menu_directions,
				android.R.drawable.ic_menu_edit,
				android.R.drawable.ic_menu_gallery,
				android.R.drawable.ic_menu_help,
				android.R.drawable.ic_menu_info_details,
				android.R.drawable.ic_menu_manage,
				android.R.drawable.ic_menu_mapmode,
				android.R.drawable.ic_menu_month,
				android.R.drawable.ic_menu_more,
				android.R.drawable.ic_menu_mylocation,
				android.R.drawable.ic_menu_myplaces,
				android.R.drawable.ic_menu_preferences,
				android.R.drawable.ic_menu_revert,
				android.R.drawable.ic_menu_upload,
				android.R.drawable.ic_menu_sort_by_size, };

		LayoutInflater mLayoutInflater;
		List<ActionItemText> mItems;

		public CustomAdapter(Context context) {
			mLayoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			mItems = new ArrayList<ActionItemText>();
			int total = (int) (4 + (Math.random() * 30));

			for (int i = 0; i < total; i++) {
				ActionItemText item = new ActionItemText(context, "Title " + i,
						ICONS[(int) (Math.random() * ICONS.length)]);
				mItems.add(item);
			}
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mItems.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View view = mLayoutInflater.inflate(R.layout.action_item, arg2,
					false);

			ActionItemText item = (ActionItemText) getItem(position);

			ImageView image = (ImageView) view.findViewById(R.id.image);
			TextView text = (TextView) view.findViewById(R.id.title);

			image.setImageDrawable(item.getIcon());
			text.setText(item.getTitle());

			return view;
		}

	};
}
