package com.example.androidthreadstests.tasks.impl;

import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.example.androidthreadstests.cache.DiskCache;
import com.example.androidthreadstests.cache.MemoryCache;
import com.example.androidthreadstests.models.BaseGalleryModel;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.tasks.Downloader;

public class FlickrDownloader extends Downloader<ImageView, GalleryItem, Bitmap> {

	public FlickrDownloader(Handler target, Context context) {
		super(target, context);
	}

	@Override
	protected Bitmap handleRequest(GalleryItem model) {
		Bitmap bmp = null;
		if(model == null) {
			bmp = null;
		}
		
		try{
			if(MemoryCache.getInstance().contains(model.getId())){
				bmp = MemoryCache.getInstance().get(model.getId());
				Log.d(getClass().getSimpleName(), "Loaded from MEMORY");
			}else{
				String urlString = getURL(model);
				bmp = DiskCache.getInstance().get(model.getId(), 75, 75);
				if(bmp != null) {
					Log.d(getClass().getSimpleName(), "Loaded from DISK");
					return bmp;
				}else{
					URL url = new URL(urlString);
					URLConnection conn = url.openConnection();
					DiskCache.getInstance().put(model.getId(), conn.getInputStream(), 75, 75);
					if(MemoryCache.getInstance().contains(model.getId())) {
						Log.d(getClass().getSimpleName(), "Loaded from web");
						bmp =  MemoryCache.getInstance().get(model.getId()); 
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return bmp;
	}

	protected String getURL(GalleryItem model) {
		StringBuilder builder = new StringBuilder();
		builder.append("http://farm");
		builder.append(((GalleryItem)model).getFarm());
		builder.append(".staticflickr.com/");
		builder.append(((GalleryItem)model).getServer());
		builder.append("/");
		builder.append(((GalleryItem)model).getId());
		builder.append("_");
		builder.append(((GalleryItem)model).getSecret());
		builder.append(".jpg");
		
		return builder.toString();
	}
}
