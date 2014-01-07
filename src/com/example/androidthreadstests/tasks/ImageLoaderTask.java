package com.example.androidthreadstests.tasks;

import java.net.URL;
import java.net.URLConnection;

import com.example.androidthreadstests.models.BaseGalleryModel;
import com.example.androidthreadstests.tasks.listeners.LoadImageListener;

public abstract class ImageLoaderTask implements Runnable {

	private LoadImageListener mListener;
	private BaseGalleryModel mModel;
	public ImageLoaderTask(BaseGalleryModel model, LoadImageListener listener) {
		mListener = listener;
		mModel = model;
	}
	
	@Override
	public void run() {
		
		try{
			String urlString = getURL(mModel);
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			
		}catch(Exception e){
			mListener.onLoadError(mModel.getId());
		}
	}

	protected abstract String getURL(BaseGalleryModel model);

}
