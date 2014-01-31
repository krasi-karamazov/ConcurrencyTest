package com.example.androidthreadstests.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.example.androidthreadstests.R;
import com.example.androidthreadstests.adapters.GalleryAdapter;
import com.example.androidthreadstests.loaders.DataType;
import com.example.androidthreadstests.loaders.StructuredDocumentLoader;
import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.parser.FlickrJSONParser;
import com.example.androidthreadstests.parser.FlikrParser;
import com.example.androidthreadstests.tasks.listeners.DownloadListener;
import com.example.androidthreadstests.utils.Constants;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by krasimir.karamazov on 1/14/14.
 */
public class MainFragment extends Fragment implements DownloadListener<List<GalleryItem>> {

    private GridView mGridView;
    private List<GalleryItem> mItems;
    private GalleryAdapter mAdapter;

    public static final MainFragment getInstance(Bundle args) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        final Uri completeQuery = Uri.parse(Constants.ENDPOINT).buildUpon().appendQueryParameter(Constants.METHOD_PARAM_NAME, Constants.GET_RECENT_METHOD)
                .appendQueryParameter(Constants.API_KEY_PARAM_NAME, Constants.API_KEY)
                .appendQueryParameter(Constants.FORMAT_PARAM_NAME, Constants.FORMAT_JSON)
                .appendQueryParameter(Constants.NO_JSON_CALLBACK_ARG_NAME, Constants.NO_JSON_CALLBACK_ARG)
                /*.appendQueryParameter(Constants.PAGE_ARG_NAME, Integer.valueOf(Constants.CURRENT_PAGE).toString())
                .appendQueryParameter(Constants.PER_PAGE_ARG_NAME, Constants.PER_PAGE_ARG)*/
                .appendQueryParameter("extras", "url_q")
                .build();
        StructuredDocumentLoader<DataType.XML, List<GalleryItem>> downloader = new StructuredDocumentLoader<DataType.XML, List<GalleryItem>>();
        downloader.addTask(completeQuery.toString(), "MainActivity", new FlickrJSONParser(), this);
        downloader.startAllTasks();

        mItems = new LinkedList<GalleryItem>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mGridView = (GridView)rootView.findViewById(R.id.gv_grid);


        mAdapter = new GalleryAdapter(getActivity(), mItems);
        mGridView.setAdapter(mAdapter);

        return rootView;
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
}
