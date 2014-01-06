package com.example.androidthreadstests.tasks.listeners;

import java.util.List;

import com.example.androidthreadstests.models.GalleryItem;

public interface MainDataListener {
	public void mainDataLoadedAndParsed(List<GalleryItem> list);
	public void mainDataLoadError(String message);
}
