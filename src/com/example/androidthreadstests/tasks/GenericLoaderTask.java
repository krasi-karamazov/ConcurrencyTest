package com.example.androidthreadstests.tasks;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.androidthreadstests.tasks.listeners.DownloadListener;

import java.io.File;

/**
 * Created by krasimir.karamazov on 1/13/14.
 */
public class GenericLoaderTask {
    private String mURL;
    private DataAsyncTask mTask;
    private Handler mHandler;
    private DownloadListener<Void> mListener;

    public GenericLoaderTask(String url, File saveLocation, DownloadListener<Void> listener) {
        mURL = url;
        mListener = listener;
        mTask = new DataAsyncTask();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void execute(){
        mTask.execute(mURL);
    }

    public void dismiss(boolean interrupt){
        mTask.cancel(interrupt);
    }

    private class DataAsyncTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... strings) {

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mListener.downloadComplete(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mListener.downloadProgress(values[0]);
        }
    };
}
