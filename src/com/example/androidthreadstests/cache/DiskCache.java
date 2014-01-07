package com.example.androidthreadstests.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.androidthreadstests.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class DiskCache {
	private static File cacheDir;
	private static DiskCache sInstance;
	
	private DiskCache() {
	
	}
	
	public static void setUpStorage(Context context) {
		context = context.getApplicationContext();
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			cacheDir = context.getCacheDir();	
		}else{
			cacheDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getString(R.string.app_name));
		}
		if(!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}
	
	public static DiskCache getInstance() {
		synchronized (DiskCache.class) {
			if(sInstance == null) {
				if(cacheDir == null) {
					throw new IllegalStateException("Context is null. Did you use the static method setContext(Context) first?");
				}
				sInstance = new DiskCache();
			}
		}
		return sInstance;
	}
	
	public synchronized void put(String id, InputStream is, int width, int height) throws IOException{
		File f = new File(cacheDir, id); 
		byte[] buffer = new byte[1024];
		FileOutputStream fos = new FileOutputStream(f);
		
		while(is.read(buffer) != -1){
			fos.write(buffer);
		}
		fos.close();
		Bitmap bmp = null;
		try{
			bmp = decodeBitmap(f, width, height);
		}catch(IOException e){
			bmp = null;
		}
		ImageMemoryCache.getInstance().put(id, bmp);
	}
	
	public synchronized Bitmap get(String id, int width, int height) throws IOException{
		File f = new File(cacheDir, id); 
		if(!f.exists()){
			return null;
		}
		try{
			ImageMemoryCache.getInstance().put(id, decodeBitmap(f, width, height));
			return ImageMemoryCache.getInstance().get(id);
		}catch(IOException e){
			return null;
		}
	}
	
	private Bitmap decodeBitmap(File f, int requiredWidth, int requiredHeight) throws IOException  {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		FileInputStream fis = new FileInputStream(f);
		BitmapFactory.decodeStream(fis, null, options);
		fis.close();
		int tempWidth = options.outWidth;
		int tempHeight = options.outHeight;
		int scale = 1;
		
		while(((tempWidth / scale) > requiredWidth) && ((tempHeight / scale) > requiredHeight) ) {
			scale *=2;
		}
		BitmapFactory.Options options2 = new BitmapFactory.Options();
		options2.inSampleSize = scale;
		FileInputStream fis2 = new FileInputStream(f);
		Bitmap bmp = BitmapFactory.decodeStream(fis2, null, options2);
		fis2.close();
		return bmp;
	}
}
