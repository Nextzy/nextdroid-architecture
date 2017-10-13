package com.nextzy.tabcustomize.base.repository.network

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import com.nextzy.nextwork.engine.model.NetworkStatus
import com.nextzy.nextwork.engine.model.NextworkResource
import com.nextzy.nextwork.engine.model.Status


/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */
class DefaultResource<T>(
        @NonNull @Status status: Int,
        @Nullable data: T,
        @Nullable message: String?)
    : NextworkResource<T>(status, data, message) {

    companion object {
        fun <T> success(@Nullable data: T): DefaultResource<T> {
            return DefaultResource(NetworkStatus.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable?, @Nullable data: T): DefaultResource<T> {
            return DefaultResource(NetworkStatus.ERROR, data, error?.message)
        }

        fun <T> loading(@Nullable data: T): DefaultResource<T> {
            return DefaultResource(NetworkStatus.LOADING, data, null)
        }
    }
}