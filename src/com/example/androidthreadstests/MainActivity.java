package com.example.androidthreadstests;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;

import com.example.androidthreadstests.adapters.GalleryAdapter;
import com.example.androidthreadstests.loaders.DataType;
import com.example.androidthreadstests.loaders.StructuredDocumentLoader;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.parser.FlikrParser;
import com.example.androidthreadstests.loaders.galleryloader.ImageLoader;
import com.example.androidthreadstests.tasks.listeners.DownloadListener;
import com.example.androidthreadstests.utils.Constants;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends android.app.Activity implements DownloadListener<List<GalleryItem>> {
	private GridView mGridView;
	private List<GalleryItem> mItems;
	private GalleryAdapter mAdapter;
	private boolean mInProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loadCurrentPage();


		mGridView = (GridView)findViewById(R.id.gv_grid);

        final Uri completeQuery = Uri.parse(Constants.ENDPOINT).buildUpon().appendQueryParameter(Constants.METHOD_PARAM_NAME, Constants.GET_RECENT_METHOD)
                .appendQueryParameter(Constants.API_KEY_PARAM_NAME, Constants.API_KEY)
                .appendQueryParameter(Constants.FORMAT_PARAM_NAME, Constants.FORMAT)
                .appendQueryParameter(Constants.PAGE_ARG_NAME, Integer.valueOf(Constants.CURRENT_PAGE).toString())
                .appendQueryParameter(Constants.PER_PAGE_ARG_NAME, Constants.PER_PAGE_ARG)
                .appendQueryParameter("extras", "url_q")
                .build();
        StructuredDocumentLoader<DataType.XML, List<GalleryItem>> downloader = new StructuredDocumentLoader<DataType.XML, List<GalleryItem>>();
        downloader.addTask(completeQuery.toString(), "MainActivity", new FlikrParser(), this);
        downloader.startAllTasks();

		mItems = new LinkedList<GalleryItem>();
        mAdapter = new GalleryAdapter(this, mItems);
        mGridView.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void loadCurrentPage(){

    }

    @Override
    public void downloadComplete(List<GalleryItem> result) {
        mItems.addAll(result);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void downloadProgress(Integer progress) {
        Log.d("PARSER ", progress + "");
    }

    @Override
    public void downloadError(String message) {
        Log.d("PARSER ", "Error" + message);
    }
	
	@Override
	protected void onStop() {
		super.onStop();
		ImageLoader.getInstance(this).killDownloads();
	}
}
