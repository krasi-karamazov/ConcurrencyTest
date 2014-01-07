package com.example.androidthreadstests.ui;

import com.example.androidthreadstests.tasks.ImageLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class NetworkImageView extends ImageView {

	private ImageLoader mListener;
	public NetworkImageView(Context context) {
		super(context);
	}
	
	public NetworkImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public NetworkImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void loadImage(String str) {
	}
	
}
