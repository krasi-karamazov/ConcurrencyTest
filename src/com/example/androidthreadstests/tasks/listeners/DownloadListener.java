package com.example.androidthreadstests.tasks.listeners;

public interface DownloadListener<Result> {
	public void downloadComplete(Result result);
    public void downloadProgress(Integer progress);
	public void downloadError(String message);
}
