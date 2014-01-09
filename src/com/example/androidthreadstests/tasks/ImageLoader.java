package com.example.androidthreadstests.tasks;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.androidthreadstests.cache.DiskCache;
import com.example.androidthreadstests.cache.MemoryCache;
import com.example.androidthreadstests.models.BaseGalleryModel;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.tasks.impl.FlickrImageTask;
import com.example.androidthreadstests.tasks.listeners.LoadImageListener;
import com.example.androidthreadstests.ui.NetworkImageView;
import com.example.androidthreadstests.utils.Constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader {
	
	private ExecutorService mExecutorService;
	private Context mContext;
	private Handler mHandler;
	private static ImageLoader sInstance;
	private Map<NetworkImageView, BaseGalleryModel> mMap = Collections.synchronizedMap(new WeakHashMap<NetworkImageView, BaseGalleryModel>());
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
	
	public void loadImage(BaseGalleryModel item, NetworkImageView imgView) {
		mMap.put(imgView, item);
		enqueueImage(imgView);
		
	}

	private void enqueueImage(NetworkImageView imgView) {
		final BaseGalleryModel model = mMap.get(imgView);
		if(model == null) {
			return;
		}
		FlickrImageTask task = new FlickrImageTask(imgView, model, new NetworkListener(), this);
		mExecutorService.submit(task);
	}
	
	private class NetworkListener implements LoadImageListener{
		
		
		@Override
		public void onLoadCompleted(final Bitmap bmp, final NetworkImageView view, final BaseGalleryModel model) {
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
		public void onLoadError(String id, NetworkImageView view) {
			Log.d(getClass().getSimpleName(), "Error with " + id);
		}
	}
	
	public boolean isViewReused(BaseGalleryModel model, ImageView view) {
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
