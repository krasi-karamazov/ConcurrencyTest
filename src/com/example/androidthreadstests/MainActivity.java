package com.example.androidthreadstests;

import java.util.LinkedList;
import java.util.List;

import com.example.androidthreadstests.adapters.GalleryAdapter;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.tasks.ImageLoader;
import com.example.androidthreadstests.tasks.MainDataLoader;
import com.example.androidthreadstests.tasks.listeners.MainDataListener;
import com.example.androidthreadstests.utils.Constants;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ListView;

public class MainActivity extends Activity implements MainDataListener {
	private GridView mGridView;
	private List<GalleryItem> mItems;
	private GalleryAdapter mAdapter;
	private MainDataLoader mLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGridView = (GridView)findViewById(R.id.gv_grid);
		mItems = new LinkedList<GalleryItem>();
		
		mLoader = new MainDataLoader(this);
		mLoader.execute(Constants.CURRENT_PAGE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void mainDataLoadedAndParsed(List<GalleryItem> list) {
		Log.d("PARSED", "CURRENT PAGE " + Constants.CURRENT_PAGE);
		if(mAdapter == null){
			mItems.addAll(list);
			mAdapter = new GalleryAdapter(this, mItems);
			mGridView.setAdapter(mAdapter);
		}else{
			mItems.addAll(list);
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void mainDataLoadError(String message) {
		Log.d("ERROR", message);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		ImageLoader.getInstance(this).killDownloads();
	}
}
