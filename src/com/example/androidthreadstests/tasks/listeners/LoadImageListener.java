package com.example.androidthreadstests.tasks.listeners;

public interface LoadImageListener {
	public void onLoadCompleted();
	public void onLoadError(String id);
}
