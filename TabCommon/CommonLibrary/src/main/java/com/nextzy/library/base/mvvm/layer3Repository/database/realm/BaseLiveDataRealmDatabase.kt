package com.nextzy.library.base.mvvm.layer3Repository.database.realm

import android.arch.lifecycle.LiveData
import io.realm.RealmObject
import io.realm.Sort

/**
 * Created by「 The Khaeng 」on 11 Oct 2017 :)
 */


abstract class BaseLiveDataRealmDatabase : BaseRealmDatabase() {


    fun <T : RealmObject> queryAsLiveData(fieldName: String,
                                          value: String,
                                          realmClass: Class<T>): LiveData<T>
            = getRealm().where(realmClass)
            .equalTo(fieldName, value)
            .findFirstAsync()
            .asLiveData()


    fun <T : RealmObject> queryAllAsLiveData(fieldName: List<String>? = null,
                                             order: List<Sort>? = null,
                                             realmClass: Class<T>): LiveData<List<T>>
            = if (fieldName != null && order != null) {
        getRealm().where(realmClass)
                .findAllSortedAsync(
                        fieldName.toTypedArray(),
                        order.toTypedArray())
                .asLiveData()
    } else {
        getRealm().where(realmClass).findAllAsync().asLiveData()
    }
//
//
//        emitter.setDisposable(Disposables.fromRunnable {
//            result?.removeAllChangeListeners()
//            close(realm)
//            if (Looper.getMainLooper() != looper) {
//                looper.thread.interrupt()
//            }
//        })


}