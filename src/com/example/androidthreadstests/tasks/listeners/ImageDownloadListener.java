package com.example.androidthreadstests.tasks.listeners;

public interface ImageDownloadListener<T, D> {
	public void onImageDownloaded(T token, D data);
}
