package com.example.androidthreadstests.tasks.listeners;

import java.lang.ref.WeakReference;

import com.example.androidthreadstests.models.BaseGalleryModel;
import com.example.androidthreadstests.ui.NetworkImageView;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface LoadImageListener {
	public void onLoadCompleted(final Bitmap bmp, NetworkImageView view, BaseGalleryModel model);
	public void onLoadError(String id, NetworkImageView view);
}
