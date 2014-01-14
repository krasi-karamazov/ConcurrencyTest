package com.example.androidthreadstests.tasks;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.androidthreadstests.exceptions.ParseException;
import com.example.androidthreadstests.parser.BaseParser;
import com.example.androidthreadstests.parser.listeners.ParserListener;
import com.example.androidthreadstests.tasks.listeners.DownloadListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by krasimir.karamazov on 1/13/14.
 */
public class StructuredDocumentLoaderTask<Result> implements ParserListener<Result>{
    private String mURL;
    private BaseParser<Result> mParser;
    private DownloadListener<Result> mListener;
    private DataAsyncTask mTask;
    private Handler mHandler;

    public StructuredDocumentLoaderTask(String url, BaseParser<Result> parser, DownloadListener<Result> listener) {
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

    private class DataAsyncTask extends AsyncTask<String, Integer, Result>{
        @Override
        protected Result doInBackground(String... strings) {
            String urlString = strings[0];
            Result result;
            try {
                HttpGet getMethod = new HttpGet(urlString);
                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(getMethod);

                InputStream is = response.getEntity().getContent();
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
        protected void onPostExecute(Result result) {
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
