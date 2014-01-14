package com.example.androidthreadstests.tasks;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.androidthreadstests.tasks.listeners.DownloadListener;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by krasimir.karamazov on 1/13/14.
 */
public class GenericLoaderTask {
    private String mURL;
    private DataAsyncTask mTask;
    private DownloadListener<File> mListener;
    private File mSaveLocation;

    public GenericLoaderTask(String url, File saveLocation, DownloadListener<File> listener) {
        mURL = url;
        mListener = listener;
        mTask = new DataAsyncTask();
        mSaveLocation = saveLocation;

    }

    public void execute(){
        mTask.execute(mURL);
    }

    public void dismiss(boolean interrupt){
        mTask.cancel(interrupt);
    }

    private class DataAsyncTask extends AsyncTask<String, Integer, File> {
        @Override
        protected File doInBackground(String... strings) {
            if(mSaveLocation.exists()){
                mSaveLocation.mkdirs();
            }
            try {
                HttpGet getMethod = new HttpGet(strings[0]);
                HttpClient client = new DefaultHttpClient();

                HttpResponse response = client.execute(getMethod);

                final InputStream is = response.getEntity().getContent();

                final long totalBytes = response.getEntity().getContentLength();
                long currentBytes = 0L;

                final FileOutputStream fos = new FileOutputStream(mSaveLocation);
                final byte[] buffer = new byte[1024];
                int numRead;
                while((numRead = is.read(buffer)) != -1){
                    if(totalBytes > 0){
                        currentBytes += numRead;
                        long percent = Math.round(((double) currentBytes / (double) totalBytes) * 100);
                        publishProgress((int)percent);
                    }
                    fos.write(buffer);
                }
                fos.flush();
                is.close();
                fos.close();

            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }


            return mSaveLocation;
        }

        @Override
        protected void onPostExecute(File result) {
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
