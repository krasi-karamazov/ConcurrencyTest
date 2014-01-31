package com.example.androidthreadstests.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by krasimir.karamazov on 1/31/14.
 */
public abstract class BaseJsonParser<Result> extends BaseParser<Result> {

    @Override
    public final Result parse(InputStream inputData) {
        JsonFactory factory = getJsonFactory();
        JsonParser parser = null;
        try{
            parser = factory.createJsonParser(inputData);

            while(parser.nextToken() != JsonToken.END_OBJECT) {
                readObjectsFromJson(parser);

            }
        }catch(JsonParseException e){
            return null;
        }catch(IOException ioe){
            return null;
        }
        try{
            parser.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return getResult();
    }

    protected abstract JsonFactory getJsonFactory();
    protected abstract void readObjectsFromJson(JsonParser parser) throws IOException;
}
