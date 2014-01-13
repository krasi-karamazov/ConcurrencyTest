package com.example.androidthreadstests.tasks.impl;

import android.widget.ImageView;

import com.example.androidthreadstests.models.BaseLoaderModel;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.tasks.ImageLoaderTask;
import com.example.androidthreadstests.tasks.listeners.LoadImageListener;

public class FlickrImageTask extends ImageLoaderTask {

	public FlickrImageTask(ImageView view, BaseLoaderModel<String> model, LoadImageListener listener) {
		super(view, model, listener);
	}

	@Override
	protected String getURL(BaseLoaderModel model) {
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
