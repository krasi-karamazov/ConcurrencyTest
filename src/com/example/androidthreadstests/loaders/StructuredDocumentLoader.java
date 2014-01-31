package com.example.androidthreadstests.loaders;

import com.example.androidthreadstests.parser.BaseParser;
import com.example.androidthreadstests.parser.BaseXMLParser;
import com.example.androidthreadstests.tasks.StructuredDocumentLoaderTask;
import com.example.androidthreadstests.tasks.listeners.DownloadListener;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class StructuredDocumentLoader<DataType, Result> {
    private Map<String, StructuredDocumentLoaderTask<Result>> mTasks = Collections.synchronizedMap(new TreeMap<String, StructuredDocumentLoaderTask<Result>>());

    public void addTask(String url, String taskTag, BaseParser<Result> parser, DownloadListener<Result> listener){
        mTasks.put(taskTag, new StructuredDocumentLoaderTask<Result>(url, parser, listener));
    }

    public void startAllTasks(){
        Iterator<Map.Entry<String, StructuredDocumentLoaderTask<Result>>> iterator = mTasks.entrySet().iterator();
        while(iterator.hasNext()){
            iterator.next().getValue().execute();
        }
    }

    public void stopAllTasks(boolean interrupt){
        Iterator<Map.Entry<String, StructuredDocumentLoaderTask<Result>>> iterator = mTasks.entrySet().iterator();
        while(iterator.hasNext()){
            iterator.next().getValue().dismiss(interrupt);
        }
    }

    public void stopTask(String tag, boolean interrupt) {
        StructuredDocumentLoaderTask<Result> task = mTasks.get(tag);
        if(task != null) {
            task.dismiss(interrupt);
        }
    }

    public void startTask(String tag, boolean interrupt) {
        StructuredDocumentLoaderTask<Result> task = mTasks.get(tag);
        if(task != null) {
            task.dismiss(interrupt);
        }
    }
}
