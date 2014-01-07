package com.example.androidthreadstests;

import java.util.ArrayList;
import java.util.List;
import com.example.androidthreadstests.adapters.GalleryAdapter;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.tasks.MainDataLoader;
import com.example.androidthreadstests.tasks.listeners.MainDataListener;
import com.example.androidthreadstests.utils.Constants;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

public class MainActivity extends Activity implements MainDataListener {
	private GridView mGridView;
	private List<GalleryItem> mItems;
	private GalleryAdapter mAdapter;
	private MainDataLoader mLoader;
	private boolean mLoading = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGridView = (GridView)findViewById(R.id.gv_grid);
		mItems = new ArrayList<GalleryItem>();
		mAdapter = new GalleryAdapter(this, mItems);
		mGridView.setAdapter(mAdapter);
		mLoader = new MainDataLoader(this);
		mLoader.execute(Constants.CURRENT_PAGE);
		mGridView.setOnScrollListener(getOnScrollListener());
	}

	private OnScrollListener getOnScrollListener() {
		
		return new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				int lastItem = firstVisibleItem + visibleItemCount;
				if(lastItem == totalItemCount){
					if(Constants.CURRENT_PAGE < Constants.NUM_PAGES) {
						if(!mLoading){
							Constants.CURRENT_PAGE += 1;
							quitCurrentPageStartNextChoice();
							mLoading = true;
						}
						
					}else{
						return;
					}
				}
			}
		};
	}
	
	private void quitCurrentPageStartNextChoice() {
		if(mLoader != null) {
			mLoader.cancel(false);
			mLoader = new MainDataLoader(this);
			mLoader.execute(Constants.CURRENT_PAGE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void mainDataLoadedAndParsed(List<GalleryItem> list) {
		Log.d("PARSED", "CURRENT PAGE " + Constants.CURRENT_PAGE);
		mAdapter.addAll(list);
		mAdapter.notifyDataSetChanged();
		mLoading = false;
	}

	@Override
	public void mainDataLoadError(String message) {
		Log.d("ERROR", message);
	}
}
