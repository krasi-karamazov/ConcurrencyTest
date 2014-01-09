package com.example.androidthreadstests.ui;

import com.example.androidthreadstests.cache.MemoryCache;
import com.example.androidthreadstests.models.BaseGalleryModel;
import com.example.androidthreadstests.tasks.ImageLoader;
import com.example.androidthreadstests.tasks.Downloader;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class NetworkImageView extends ImageView {
	public NetworkImageView(Context context) {
		super(context);
	}
	
	public NetworkImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public NetworkImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void loadImage(BaseGalleryModel model) {
		if(MemoryCache.getInstance().contains(model.getId())) {
			setImageBitmap(MemoryCache.getInstance().get(model.getId()));
			Log.d(getClass().getSimpleName(), "Loaded from memory");
		}else{
			ImageLoader.getInstance(getContext()).loadImage(model, this);
		}
	}
	
}
