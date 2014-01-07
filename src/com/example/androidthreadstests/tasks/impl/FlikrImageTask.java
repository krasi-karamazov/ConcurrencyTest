package com.example.androidthreadstests.tasks.impl;

import com.example.androidthreadstests.models.BaseGalleryModel;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.tasks.ImageLoaderTask;
import com.example.androidthreadstests.tasks.listeners.LoadImageListener;

public class FlikrImageTask extends ImageLoaderTask {

	public FlikrImageTask(BaseGalleryModel model, LoadImageListener listener) {
		super(model, listener);
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
