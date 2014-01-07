package com.example.androidthreadstests.tasks;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.androidthreadstests.cache.DiskCache;
import com.example.androidthreadstests.models.BaseGalleryModel;
import com.example.androidthreadstests.tasks.impl.FlikrImageTask;
import com.example.androidthreadstests.tasks.listeners.LoadImageListener;
import com.example.androidthreadstests.utils.Constants;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

public class ImageLoader {
	WeakReference<ImageView> mTarget;
	private ExecutorService mExecutorService;
	private Context mContext;
	private Handler mHandler;
	public ImageLoader(ImageView imgView, Context context) {
		mTarget = new WeakReference<ImageView>(imgView);
		mContext = context.getApplicationContext();
		mExecutorService = Executors.newFixedThreadPool(Constants.THREADPOOL_SIZE);
		mHandler = new Handler(Looper.getMainLooper());
		DiskCache.setUpStorage(mContext);
	}
	
	public void loadImage(BaseGalleryModel item) {
		enqueueImage(new FlikrImageTask(item, getLoadImageListener()));
	}

	private void enqueueImage(ImageLoaderTask imageLoaderTask) {
		mExecutorService.execute(imageLoaderTask);
	}

	private LoadImageListener getLoadImageListener() {
		
		return new LoadImageListener() {
			
			@Override
			public void onLoadError(String id) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadCompleted() {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
