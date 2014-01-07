package com.example.androidthreadstests.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidthreadstests.R;
import com.example.androidthreadstests.models.GalleryItem;

public class GalleryAdapter extends BaseAdapter {

	static class ViewHolder{
		ImageView imgView;
		TextView label;
		String id;
		Bitmap bmp;
	}
	
	private Context mContext;
	List<GalleryItem> mObjects;
	public GalleryAdapter(Context context, List<GalleryItem> objects) {
		mContext = context;
		mObjects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder;
		if(row == null) {
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.layout_list_item, parent, false);
			holder = new ViewHolder();
			holder.imgView = (ImageView)row.findViewById(R.id.iv_img);
			holder.label = (TextView)row.findViewById(R.id.tv_label);
			holder.id = ((GalleryItem)getItem(position)).getId();
			row.setTag(holder);
		}
		
		holder = (ViewHolder)row.getTag();
		holder.label.setText(((GalleryItem)getItem(position)).getTitle());
		if(holder.bmp != null) {
			holder.imgView.setImageBitmap(holder.bmp);
		}else{
			
		}
		return row;
	}

	@Override
	public int getCount() {
		return mObjects.size();
	}

	@Override
	public Object getItem(int position) {
		return mObjects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	public void addAll(List<GalleryItem> objects){
		mObjects.addAll(objects);
	}

}
