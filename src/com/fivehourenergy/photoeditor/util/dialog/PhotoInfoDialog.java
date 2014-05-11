package com.fivehourenergy.photoeditor.util.dialog;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fivehourenergy.photoeditor.R;
import com.fivehourenergy.photoeditor.data.model.ImageInfo;
import com.fivehourenergy.photoeditor.data.model.ImageInfo.Info;

public class PhotoInfoDialog extends BaseDialogNoTitle{

	private ImageInfo mImageInfo;
	
	public PhotoInfoDialog(Context context, Object datas) {
		super(context, datas);
	}

	@Override
	protected int getContentResId() {
		return R.layout.image_info_layout;
	}

	@Override
	protected void initContentView(LayoutInflater inflater, View contentView) {
		ListView listview = (ListView) contentView.findViewById(R.id.listview);
		Button button = (Button) contentView.findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		if (mDatas instanceof ImageInfo) {
			mImageInfo = ((ImageInfo)mDatas);
			listview.setAdapter( new MyListAdapter( getContext(), android.R.layout.simple_list_item_2, mImageInfo.getInfo() ) );
		}
//		getListView().setOnItemClickListener( this );
	}

	private class MyListAdapter extends ArrayAdapter<ImageInfo.Info> {

		private LayoutInflater mInflater;
		private int mResourceId;

		public MyListAdapter( Context context, int resource, List<Info> objects ) {
			super( context, resource, objects );

			mInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			mResourceId = resource;
		}

		@Override
		public View getView( int position, View convertView, ViewGroup parent ) {

			View view;
			ImageInfo.Info info = getItem( position );

			if ( convertView == null ) {
				view = mInflater.inflate( mResourceId, parent, false );
			} else {
				view = convertView;
			}

			TextView t1 = (TextView) view.findViewById( android.R.id.text1 );
			TextView t2 = (TextView) view.findViewById( android.R.id.text2 );
			t1.setText( info.getTag() );
			t2.setText( info.getValue() );
			return view;
		}
	}
	
}
