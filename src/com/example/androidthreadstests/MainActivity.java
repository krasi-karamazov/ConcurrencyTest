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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends Activity implements MainDataListener {
	private GridView mGridView;
	private List<GalleryItem> mItems;
	private GalleryAdapter mAdapter;
	private MainDataLoader mLoader;
	private boolean mInProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLoader = new MainDataLoader(this);
		loadCurrentPage();
		mGridView = (GridView)findViewById(R.id.gv_grid);
		mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if((firstVisibleItem + visibleItemCount) == totalItemCount) {
					if(!mInProgress){
						if(Constants.CURRENT_PAGE < Constants.NUM_PAGES) {
							Constants.CURRENT_PAGE += 1;
						}
						loadCurrentPage();
					}
				}
			}
		});
		mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@SuppressLint("NewApi")
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				startActionMode(new ActionMode.Callback() {
					
					@Override
					public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
						return false;
					}
					
					@Override
					public void onDestroyActionMode(ActionMode mode) {
						// TODO Auto-generated method stub
						
					}
					
					@SuppressLint("NewApi")
					@Override
					public boolean onCreateActionMode(ActionMode mode, Menu menu) {
						MenuInflater inflater = mode.getMenuInflater();
						inflater.inflate(R.menu.main, menu);
						return true;
					}
					
					@Override
					public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
						// TODO Auto-generated method stub
						return false;
					}
				});
				return false;
			}
		});
		mItems = new LinkedList<GalleryItem>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void loadCurrentPage() {
		mInProgress = true;
		mLoader = new MainDataLoader(this);
		mLoader.execute(Constants.CURRENT_PAGE);
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
		mInProgress = false;
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
