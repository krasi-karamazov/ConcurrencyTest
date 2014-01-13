package com.example.androidthreadstests.tasks.listeners;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.androidthreadstests.models.BaseLoaderModel;

public interface LoadImageListener {
	public void onLoadCompleted(final Bitmap bmp, ImageView view, BaseLoaderModel<String> model);
	public void onLoadError(String id, ImageView view);
}
