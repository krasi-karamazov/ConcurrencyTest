package com.example.androidthreadstests.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidthreadstests.R;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.tasks.Downloader;
import com.example.androidthreadstests.tasks.ImageLoader;
import com.example.androidthreadstests.tasks.impl.FlickrDownloader;
import com.example.androidthreadstests.tasks.listeners.ImageDownloadListener;
import com.example.androidthreadstests.ui.NetworkImageView;

public class GalleryAdapter extends ArrayAdapter<GalleryItem> {

	static class ViewHolder{
		NetworkImageView imgView;
		TextView label;
		String id;
		Bitmap bmp;
	}
	private Downloader<ImageView, GalleryItem, Bitmap> mDownloader;
	public GalleryAdapter(Context context, List<GalleryItem> objects) {
		super(context, android.R.layout.simple_list_item_1, objects);
		/*mDownloader = new FlickDownloader(new Handler(), getContext());
		mDownloader.begin();
		mDownloader.setListener(new ImageDownloadListener<ImageView, Bitmap>() {
			
			@Override
			public void onImageDownloaded(ImageView token, Bitmap data) {
				token.setImageBitmap(data);
			}
		});*/
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_list_item, parent, false);
        }
        
        GalleryItem item = getItem(position);
        NetworkImageView imageView = (NetworkImageView)convertView
                .findViewById(R.id.iv_img);
        imageView.setImageResource(android.R.drawable.ic_delete);
        imageView.loadImage(item);
        //mDownloader.queueDownload(imageView, item);
        
        return convertView;
	}
	
	public void killAllDownloads() {
		ImageLoader.getInstance(getContext()).killDownloads();
		//mDownloader.quit();
	}
}
