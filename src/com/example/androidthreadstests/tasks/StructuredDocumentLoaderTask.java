package com.example.androidthreadstests.tasks;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.androidthreadstests.exceptions.ParseException;
import com.example.androidthreadstests.parser.BaseParser;
import com.example.androidthreadstests.parser.listeners.ParserListener;
import com.example.androidthreadstests.tasks.listeners.DownloadListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by krasimir.karamazov on 1/13/14.
 */
public class StructuredDocumentLoaderTask<R> implements ParserListener<R>{
    private String mURL;
    private BaseParser<R> mParser;
    private DownloadListener<R> mListener;
    private DataAsyncTask mTask;
    private Handler mHandler;

    public StructuredDocumentLoaderTask(String url, BaseParser<R> parser, DownloadListener<R> listener) {
        mURL = url;
        mParser = parser;
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

    private class DataAsyncTask extends AsyncTask<String, Integer, R>{
        @Override
        protected R doInBackground(String... strings) {
            String urlString = strings[0];
            R result;
            try {
                URL url = new URL(urlString);
                URLConnection urlConnection = url.openConnection();
                InputStream is = urlConnection.getInputStream();
                result = mParser.parseData(is);
            } catch (MalformedURLException e) {
                return null;
            }catch (IOException ioe){
                return null;
            }catch(ParseException pe){
                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(R result) {
            super.onPostExecute(result);
            mListener.downloadComplete(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mListener.downloadProgress(values[0]);
        }
    };

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.downloadError(error);
            }
        });
    }
}
