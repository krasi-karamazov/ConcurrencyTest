package com.example.androidthreadstests.models;

import android.content.ContentValues;

/**
 * Created by krasimir.karamazov on 1/13/14.
 * Base loader model. Must be extended to use framework;
 * Optional method getContentValues for use with SQLite database
 */
public abstract class BaseLoaderModel<T> {

    private T mId;

    public T getId() {
        return mId;
    }

    public void setId(T mId) {
        this.mId = mId;
    }

    /**
     * Override this method to enable the model to form its own content values.
     *
     * @return ContentValues or null
     */
    public ContentValues getContentValues(){
        return null;
    }
}
