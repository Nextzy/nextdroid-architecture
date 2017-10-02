package com.nextzy.library.base.mvvm.layer3Repository.database;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by「 The Khaeng 」on 30 Aug 2017 :)
 */

public class TransactionResult{

    @Retention( SOURCE )
    @IntDef( {SUCCESS, FAIL } )
    public @interface Code{
    }
    public static final int SUCCESS = -1;
    public static final int FAIL = 1;

    private int code;

    public TransactionResult( @Code int code ){
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
