@file:JvmName("RealmUtility")

package com.nextzy.library.base.mvvm.layer3Repository.database.realm

import android.arch.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmObject
import io.realm.RealmResults

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

fun <T : RealmObject> RealmResults<T>.asLiveData(): LiveData<List<T>>
        = object : LiveData<List<T>>() {

    private val listener = RealmChangeListener<RealmResults<T>> { this.setValue(it) }

    override
    fun onActive() {
        this@asLiveData.addChangeListener(listener)
    }

    override
    fun onInactive() {
        this@asLiveData.removeChangeListener(listener)
    }
}

fun <T : RealmObject> RealmObject.asLiveData(): LiveData<T>
        = object : LiveData<T>() {

    private val listener = RealmChangeListener<T> { this.setValue(it) }

    override
    fun onActive() {
        this@asLiveData.addChangeListener(listener)
    }

    override
    fun onInactive() {
        this@asLiveData.removeChangeListener(listener)
    }


}

