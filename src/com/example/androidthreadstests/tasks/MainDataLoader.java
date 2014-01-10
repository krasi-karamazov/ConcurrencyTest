package com.example.androidthreadstests.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.parser.FlikrParser;
import com.example.androidthreadstests.tasks.listeners.MainDataListener;
import com.example.androidthreadstests.utils.Constants;
import android.net.Uri;
import android.os.AsyncTask;

public class MainDataLoader extends AsyncTask<Integer, Void, List<GalleryItem>> {

	private MainDataListener mListener;
	public MainDataLoader(MainDataListener listener) {
		mListener = listener;
	}
	
	@Override
	protected List<GalleryItem> doInBackground(Integer... arg0) {
		final Uri completeQuery = Uri.parse(Constants.ENDPOINT).buildUpon().appendQueryParameter(Constants.METHOD_PARAM_NAME, Constants.GET_RECENT_METHOD)
					.appendQueryParameter(Constants.API_KEY_PARAM_NAME, Constants.API_KEY)
					.appendQueryParameter(Constants.FORMAT_PARAM_NAME, Constants.FORMAT)
					.appendQueryParameter(Constants.PAGE_ARG_NAME, Integer.valueOf(Constants.CURRENT_PAGE).toString())
					.appendQueryParameter(Constants.PER_PAGE_ARG_NAME, Constants.PER_PAGE_ARG)
					.appendQueryParameter("extras", "url_q")
					.build();
		if(completeQuery == null){
			return null;
		}
		List<GalleryItem> items = null;
		InputStream is = null;
		try {
			URL url = new URL(completeQuery.toString());
			URLConnection urlConnection = url.openConnection();
			is = urlConnection.getInputStream();
			
			items = new FlikrParser().parseData(is);
			is.close();
			
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
		return items;
	}

	@Override
	protected void onPostExecute(List<GalleryItem> result) {
		super.onPostExecute(result);
		if(result != null){
			if(mListener != null) {
				mListener.mainDataLoadedAndParsed(result);
			}
		}else{
			if(mListener != null) {
				mListener.mainDataLoadError("ERROR LOADING");
			}
		}
		
	}
}
