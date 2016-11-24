package com.skyzone.gank.Util.RxJava;

/**
 * Created by Skyzone on 11/14/2016.
 */
public class ResultCodeError extends RuntimeException {

    public ResultCodeError(String detailMessage) {
        super(detailMessage);
    }
}
