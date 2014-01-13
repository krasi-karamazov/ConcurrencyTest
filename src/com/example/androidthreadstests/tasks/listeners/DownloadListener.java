package com.example.androidthreadstests.tasks.listeners;

public interface DownloadListener<R> {
	public void downloadComplete(R result);
    public void downloadProgress(Integer progress);
	public void downloadError(String message);
}
