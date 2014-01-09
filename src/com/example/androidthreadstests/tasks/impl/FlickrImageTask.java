package com.example.androidthreadstests.tasks.impl;

import com.example.androidthreadstests.models.BaseGalleryModel;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.tasks.ImageLoader;
import com.example.androidthreadstests.tasks.ImageLoaderTask;
import com.example.androidthreadstests.tasks.listeners.LoadImageListener;
import com.example.androidthreadstests.ui.NetworkImageView;

public class FlickrImageTask extends ImageLoaderTask {

	public FlickrImageTask(NetworkImageView view, BaseGalleryModel model, LoadImageListener listener, ImageLoader invoker) {
		super(view, model, listener, invoker);
	}

	@Override
	protected String getURL(BaseGalleryModel model) {
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
