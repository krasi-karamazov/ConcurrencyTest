package com.example.androidthreadstests.tasks.listeners;

import com.example.androidthreadstests.models.BaseGalleryModel;
import android.graphics.Bitmap;
import android.widget.ImageView;

public interface LoadImageListener {
	public void onLoadCompleted(final Bitmap bmp, ImageView view, BaseGalleryModel model);
	public void onLoadError(String id, ImageView view);
}
