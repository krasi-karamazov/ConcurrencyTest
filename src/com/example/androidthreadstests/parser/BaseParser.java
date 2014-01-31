package com.example.androidthreadstests.parser;

import com.example.androidthreadstests.exceptions.ParseException;

import java.io.InputStream;

/**
 * Created by krasimir.karamazov on 1/31/14.
 */
public abstract class BaseParser<Result> {
    private Result mResult;


    /**
     *
     * @param inputData
     * @return Result or null if error occurs
     */
    public final Result parseData(InputStream inputData){
        mResult = generateEmptyResult();
        try{
            mResult = parse(inputData);
        }catch(ParseException e){
            mResult = null;
        }
        return mResult;
    }

    protected abstract Result parse(InputStream inputData) throws ParseException;

    protected abstract Result generateEmptyResult();

    protected final Result getResult(){
        return mResult;
    }
}
