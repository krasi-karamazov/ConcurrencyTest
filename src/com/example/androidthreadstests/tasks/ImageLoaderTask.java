package com.example.androidthreadstests.tasks;

import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.util.Log;
import com.example.androidthreadstests.cache.DiskCache;
import com.example.androidthreadstests.cache.MemoryCache;
import com.example.androidthreadstests.models.BaseGalleryModel;
import com.example.androidthreadstests.tasks.listeners.LoadImageListener;
import com.example.androidthreadstests.ui.NetworkImageView;

public abstract class ImageLoaderTask implements Runnable {

	private LoadImageListener mListener;
	private BaseGalleryModel mModel;
	private NetworkImageView mImageView; 
	private ImageLoader mInvoker;
	public ImageLoaderTask(NetworkImageView view, BaseGalleryModel model, LoadImageListener listener, ImageLoader invoker) {
		mListener = listener;
		mModel = model;
		mImageView = view;
		mInvoker = invoker;
	}
	
	@Override
	public void run() {
		
		try{
			String urlString = getURL(mModel);
			
			Bitmap bmp = DiskCache.getInstance().get(mModel.getId(), 100, 100);
			
			if(bmp != null) {
				
				mListener.onLoadCompleted(bmp, mImageView, mModel);
				Log.d(getClass().getSimpleName(), "Loaded from disk");
			}else{
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				DiskCache.getInstance().put(mModel.getId(), connection.getInputStream(), 100, 100);
				if(MemoryCache.getInstance().contains(mModel.getId())) {
					
					mListener.onLoadCompleted(MemoryCache.getInstance().get(mModel.getId()), mImageView, mModel);
					Log.d(getClass().getSimpleName(), "Loaded from web");
				}else{
					Log.d(getClass().getSimpleName(), "ERROR 45");
					mListener.onLoadError(mModel.getId(), mImageView);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			mListener.onLoadError(mModel.getId(), mImageView);
		}
	}

	protected abstract String getURL(BaseGalleryModel model);

}
