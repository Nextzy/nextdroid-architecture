package com.nextzy.nextwork.engine.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.nextzy.nextwork.engine.model.NetworkStatus.SUCCESS;
import static com.nextzy.nextwork.engine.model.NetworkStatus.ERROR;
import static com.nextzy.nextwork.engine.model.NetworkStatus.LOADING;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Status of a resource that is provided to the UI.
 * <p>
 * These are usually created by the Repository classes where they return
 * {@code LiveData<NextworkResource<T>>} to pass back the latest data to the UI with its fetch status.
 */

@Retention(SOURCE)
@IntDef( {SUCCESS, ERROR, LOADING})
public @interface Status {}



