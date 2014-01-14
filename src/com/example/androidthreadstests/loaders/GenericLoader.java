package com.example.androidthreadstests.loaders;

import com.example.androidthreadstests.tasks.GenericLoaderTask;
import com.example.androidthreadstests.tasks.listeners.DownloadListener;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by krasimir.karamazov on 1/13/14.
 */
public class GenericLoader {

    private Map<String, GenericLoaderTask> mTasks = Collections.synchronizedMap(new TreeMap<String, GenericLoaderTask>());

    public void addTask(String url, String taskTag, File saveLocation, DownloadListener<File> listener){
        mTasks.put(taskTag, new GenericLoaderTask(url, saveLocation, listener));
    }

    public void startAllTasks(){
        Iterator<Map.Entry<String, GenericLoaderTask>> iterator = mTasks.entrySet().iterator();
        while(iterator.hasNext()){
            iterator.next().getValue().execute();
        }
    }

    public void stopAllTasks(boolean interrupt){
        Iterator<Map.Entry<String, GenericLoaderTask>> iterator = mTasks.entrySet().iterator();
        while(iterator.hasNext()){
            iterator.next().getValue().dismiss(interrupt);
        }
    }

    public void stopTask(String tag, boolean interrupt) {
        GenericLoaderTask task = mTasks.get(tag);
        if(task != null) {
            task.dismiss(interrupt);
        }
    }

    public void startTask(String tag, boolean interrupt) {
        GenericLoaderTask task = mTasks.get(tag);
        if(task != null) {
            task.dismiss(interrupt);
        }
    }
}
