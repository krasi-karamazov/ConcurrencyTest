package com.example.androidthreadstests.loaders.galleryloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.example.androidthreadstests.cache.DiskCache;
import com.example.androidthreadstests.cache.MemoryCache;
import com.example.androidthreadstests.models.BaseLoaderModel;
import com.example.androidthreadstests.tasks.impl.FlickrImageTask;
import com.example.androidthreadstests.tasks.listeners.LoadImageListener;
import com.example.androidthreadstests.utils.Constants;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {
	
	private ExecutorService mExecutorService;
	private Context mContext;
	private Handler mHandler;
	private static ImageLoader sInstance;
	private Map<ImageView, BaseLoaderModel<String>> mMap = Collections.synchronizedMap(new WeakHashMap<ImageView, BaseLoaderModel<String>>());
	private ImageLoader(Context context) {
		
		mContext = context.getApplicationContext();
		mExecutorService = Executors.newFixedThreadPool(Constants.THREADPOOL_SIZE);
		mHandler = new Handler(Looper.getMainLooper());
		DiskCache.setUpStorage(mContext);
	}
	
	public static ImageLoader getInstance(Context context) {
		if(sInstance == null) {
			sInstance = new ImageLoader(context);
		}
		return sInstance;
	}
	
	public void loadImage(BaseLoaderModel<String> item, ImageView imgView) {
		mMap.put(imgView, item);
		if(MemoryCache.getInstance().contains(item.getId())){
			imgView.setImageBitmap(MemoryCache.getInstance().get(item.getId()));
			Log.d(getClass().getSimpleName(), "Loaded from memory");
		}else{
			enqueueImage(imgView);
		}
	}

	private void enqueueImage(ImageView imgView) {
		final BaseLoaderModel<String> model = mMap.get(imgView);
		if(model == null) {
			return;
		}
		FlickrImageTask task = new FlickrImageTask(imgView, model, new NetworkListener());
		mExecutorService.submit(task);
	}
	
	private class NetworkListener implements LoadImageListener{
		
		
		@Override
		public void onLoadCompleted(final Bitmap bmp, final ImageView view, final BaseLoaderModel<String> model) {
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					if(isViewReused(model, view)){
						return;
					}
					view.setImageBitmap(bmp);
				}
			});
		}

		@Override
		public void onLoadError(String id, ImageView view) {
			Log.d(getClass().getSimpleName(), "Error with " + id);
		}
	}
	
	public boolean isViewReused(BaseLoaderModel<String> model, ImageView view) {
		if(mMap.get(view) == null || !mMap.get(view).getId().equals(model.getId())){
			return true;
		}
		return false;
	}
	
	public void killDownloads() {
		
		MemoryCache.getInstance().clear();
		DiskCache.clear();
	}
}
