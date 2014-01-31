package com.example.androidthreadstests.parser;

import android.util.Log;

import com.example.androidthreadstests.models.GalleryItem;
import com.example.androidthreadstests.utils.Constants;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Created by krasimir.karamazov on 1/31/14.
 */
public class FlickrJSONParser extends BaseJsonParser<List<GalleryItem>> {


    @Override
    protected JsonFactory getJsonFactory() {
        return new MappingJsonFactory();
    }

    @Override
    protected void readObjectsFromJson(JsonParser parser) throws IOException {
        JsonToken token = parser.nextToken();
        if (token != JsonToken.END_OBJECT) {
            String name = parser.getCurrentName();
            if(name != null){
                if(name.equals("pages")){
                    parser.nextToken();
                    try{
                        Constants.NUM_PAGES = Integer.valueOf(parser.getText());
                    }catch(NumberFormatException e) {
                        Constants.NUM_PAGES = 0;
                    }
                    Log.d("PARSER", "PAGES " + Constants.NUM_PAGES);
                }
            }
        }
    }

    @Override
    protected List<GalleryItem> generateEmptyResult() {
        return new LinkedList<GalleryItem>();
    }
}
