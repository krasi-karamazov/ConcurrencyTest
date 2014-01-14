package com.example.androidthreadstests.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidthreadstests.R;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.loaders.galleryloader.ImageLoader;

import java.util.List;

public class GalleryAdapter extends ArrayAdapter<GalleryItem> {

	static class ViewHolder{
		ImageView imgView;
		TextView label;
		String id;
		Bitmap bmp;
	}
	public GalleryAdapter(Context context, List<GalleryItem> objects) {
		super(context, android.R.layout.simple_list_item_1, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_list_item, parent, false);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.iv_img);
        imageView.setImageResource(android.R.drawable.ic_delete);
        ImageLoader.getInstance(getContext()).loadImage(getItem(position), imageView);
        return convertView;
	}
	
	public void killAllDownloads() {
		ImageLoader.getInstance(getContext()).killDownloads();
		//mDownloader.quit();
	}
}
