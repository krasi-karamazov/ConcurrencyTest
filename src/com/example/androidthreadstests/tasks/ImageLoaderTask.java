package com.example.androidthreadstests.tasks;

import android.widget.ImageView;

import com.example.androidthreadstests.models.BaseLoaderModel;
import com.example.androidthreadstests.tasks.listeners.LoadImageListener;

public abstract class ImageLoaderTask implements Runnable {

	private LoadImageListener mListener;
	private BaseLoaderModel<String> mModel;
	private ImageView mImageView; 
	public ImageLoaderTask(ImageView view, BaseLoaderModel<String> model, LoadImageListener listener) {
		mListener = listener;
		mModel = model;
		mImageView = view;
	}
	
	@Override
	public void run() {
		
		/*try{
			String urlString = getURL(mModel);
			
			Bitmap bmp = DiskCache.getInstance().get(mModel.getId(), 75, 75);
			
			if(bmp != null) {
				
				mListener.onLoadCompleted(bmp, mImageView, mModel);
				Log.d(getClass().getSimpleName(), "Loaded from disk");
			}else{
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				DiskCache.getInstance().put(mModel.getId(), connection.getInputStream(), 75, 75);
				if(MemoryCache.getInstance().contains(mModel.getId())) {
					
					mListener.onLoadCompleted(MemoryCache.getInstance().get(mModel.getId()), mImageView, mModel);
					Log.d(getClass().getSimpleName(), "Loaded from web");
				}else{
					Log.d(getClass().getSimpleName(), "ERROR 45");
					mListener.onLoadError(mModel.getId(), mImageView);
				}
				connection.disconnect();
			}
		}catch(Exception e){
			e.printStackTrace();
			mListener.onLoadError(mModel.getId(), mImageView);
		}*/
	}

	protected abstract String getURL(BaseLoaderModel<String> model);

}
