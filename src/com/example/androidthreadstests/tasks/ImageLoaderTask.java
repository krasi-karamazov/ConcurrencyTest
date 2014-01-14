package com.example.androidthreadstests.tasks;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.example.androidthreadstests.cache.DiskCache;
import com.example.androidthreadstests.cache.MemoryCache;
import com.example.androidthreadstests.models.BaseLoaderModel;
import com.example.androidthreadstests.tasks.listeners.LoadImageListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.HttpURLConnection;
import java.net.URL;

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
        Bitmap bmp;
        if(mModel == null) {
            return;
        }

        try{
            if(MemoryCache.getInstance().contains(mModel.getId())){
                bmp = MemoryCache.getInstance().get(mModel.getId());
                Log.d(getClass().getSimpleName(), "Loaded from MEMORY");
                mListener.onLoadCompleted(bmp, mImageView, mModel);
                return;
            }else{
                String urlString = getURL(mModel);
                bmp = DiskCache.getInstance().get(mModel.getId(), 120, 120);
                if(bmp != null) {
                    mListener.onLoadCompleted(bmp, mImageView, mModel);
                    return;
                }else{
                    HttpGet getMethod = new HttpGet(urlString);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(getMethod);

                    DiskCache.getInstance().put(mModel.getId(), response.getEntity().getContent(), 120, 120);
                    if(MemoryCache.getInstance().contains(mModel.getId())) {
                        Log.d(getClass().getSimpleName(), "Loaded from WEB");
                        bmp =  MemoryCache.getInstance().get(mModel.getId());
                        mListener.onLoadCompleted(bmp, mImageView, mModel);
                    }
                }
            }

        }catch(Exception e){
            mListener.onLoadError(mModel.getId(), mImageView);
        }
	}

	protected abstract String getURL(BaseLoaderModel<String> model);

}
