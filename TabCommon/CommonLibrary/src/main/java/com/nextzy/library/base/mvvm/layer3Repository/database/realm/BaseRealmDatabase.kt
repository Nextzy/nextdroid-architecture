package com.nextzy.library.base.mvvm.layer3Repository.database.realm

import android.os.HandlerThread
import android.os.Looper
import android.os.Process
import android.support.annotation.IntDef
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.realm.*
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.*

/**
 * Created by「 The Khaeng 」on 11 Oct 2017 :)
 */

abstract class BaseRealmDatabase {

    companion object {
        const val ASYNC = 1L
        const val SYNC = 2L

        @IntDef(ASYNC, SYNC)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Type
    }

    abstract fun getRealm(): Realm

    private val looper: Looper
        get() {
            return if (Looper.myLooper() != Looper.getMainLooper()) {
                val backgroundThread = HandlerThread(
                        "Scheduler-Realm-BackgroundThread",
                        Process.THREAD_PRIORITY_BACKGROUND)
                backgroundThread.start()
                backgroundThread.looper
            } else {
                Looper.getMainLooper()
            }
        }

    protected fun generateId(): String {
        return UUID.randomUUID().toString()
    }


    protected fun <T : RealmObject> save(realmObject: T): Single<T>
            = save(SYNC, realmObject)

    protected fun <T : RealmObject> saveAsync(realmObject: T): Single<T>
            = save(ASYNC, realmObject)


    protected fun <T : RealmObject> query(fieldName: String,
                                          value: String,
                                          realmClass: Class<T>): Single<T>
            = query(SYNC, fieldName, value, realmClass)

    protected fun <T : RealmObject> queryAsync(fieldName: String,
                                               value: String,
                                               realmClass: Class<T>): Single<T>
            = query(ASYNC, fieldName, value, realmClass)

    protected fun <T : RealmObject> queryAll(fieldName: List<String>? = null,
                                             order: List<Sort>? = null,
                                             realmClass: Class<T>): Single<List<T>>
            = queryAll(SYNC, fieldName, order, realmClass)

    protected fun <T : RealmObject> queryAllAsync(fieldName: List<String>? = null,
                                                  order: List<Sort>? = null,
                                                  realmClass: Class<T>): Single<List<T>>
            = queryAll(ASYNC, fieldName, order, realmClass)

    protected fun <T : RealmObject> delete(fieldName: String? = null,
                                           value: String? = null,
                                           realmClass: Class<T>): Single<T>
            = delete(SYNC, fieldName, value, realmClass)

    protected fun <T : RealmObject> deleteAsync(fieldName: String? = null,
                                                value: String? = null,
                                                realmClass: Class<T>): Single<T>
            = delete(ASYNC, fieldName, value, realmClass)


    protected fun deleteAllDatabase() {
        getRealm().executeTransaction { realm1 -> realm1.deleteAll() }
    }

    /* =========================== Private method =============================================== */


    private fun <T : RealmObject> save(@Type type: Long,
                                       realmObject: T): Single<T>
            = Single.create { singleEmitter ->
        val realm = getRealm()

        if (type == SYNC) {
            realm.executeTransaction({ realm1 ->
                                         realm1.copyToRealmOrUpdate(realmObject)
                                         singleEmitter.onSuccess(realmObject)
                                     })
        } else if (type == ASYNC) {
            realm.executeTransactionAsync(
                    { realm1 -> realm1.copyToRealmOrUpdate(realmObject) },
                    {
                        singleEmitter.onSuccess(realmObject)
                        close(realm)
                    })
            { throwable ->
                singleEmitter.onError(throwable)
                close(realm)
            }
        }
        singleEmitter.setDisposable(Disposables.fromRunnable { close(realm) })

    }


    private fun <T : RealmObject> query(@Type type: Long,
                                        fieldName: String,
                                        value: String,
                                        cls: Class<T>): Single<T>
            = Single.create<T> { emitter ->
        val looper = looper
        val realm = getRealm()

        var result: T? = null
        if (type == SYNC) {
            result = realm.where(cls)
                    .equalTo(fieldName, value)
                    .findFirst()
            if (isNotNullObject(emitter, result)) {
                emitter.onSuccess(realm.copyToRealm(result))
            }
        } else if (type == ASYNC) {
            result = realm.where(cls)
                    .equalTo(fieldName, value)
                    .findFirstAsync()
            if (isNotNullObject(emitter, result)) {
                result.addChangeListener(RealmObjectChangeListener<T> { o, changeSet ->
                    if (isValid(emitter, result)) {
                        emitter.onSuccess(realm.copyFromRealm(o))
                    }
                })
            }
        }


        emitter.setDisposable(Disposables.fromRunnable {
            result?.removeAllChangeListeners()
            close(realm)
            if (Looper.getMainLooper() != looper) {
                looper.thread.interrupt()
            }

        })
    }.subscribeOn(AndroidSchedulers.from(looper))
            .unsubscribeOn(AndroidSchedulers.from(looper))


    private fun <T : RealmObject> queryAll(@Type type: Long,
                                           fieldName: List<String>? = null,
                                           order: List<Sort>? = null,
                                           cls: Class<T>): Single<List<T>>
            = Single.create<List<T>> { emitter ->
        val looper = looper
        val realm = getRealm()

        var result: RealmResults<T>? = null
        if (fieldName != null && order != null) {
            if (type == SYNC) {
                result = realm.where(cls)
                        .findAllSorted(
                                fieldName.toTypedArray(),
                                order.toTypedArray())
                emitter.onSuccess(realm.copyFromRealm(result))
            } else if (type == ASYNC) {
                result = realm.where(cls)
                        .findAllSortedAsync(
                                fieldName.toTypedArray(),
                                order.toTypedArray())
                result?.addChangeListener { o: RealmResults<T> ->
                    if (isValid(emitter, result)) {
                        emitter.onSuccess(realm.copyFromRealm(o))
                    }
                }
            }
        } else {
            if (type == SYNC) {
                result = realm.where(cls).findAll()
                emitter.onSuccess(realm.copyFromRealm(result))
            } else if (type == ASYNC) {
                result = realm.where(cls).findAllAsync()
                result?.addChangeListener { o: RealmResults<T> ->
                    if (isValid(emitter, result)) {
                        emitter.onSuccess(realm.copyFromRealm(o))
                    }
                }
            }
        }


        emitter.setDisposable(Disposables.fromRunnable {
            result?.removeAllChangeListeners()
            close(realm)
            if (Looper.getMainLooper() != looper) {
                looper.thread.interrupt()
            }
        })
    }.subscribeOn(AndroidSchedulers.from(looper))
            .unsubscribeOn(AndroidSchedulers.from(looper))

    private fun <T : RealmObject> delete(@Type type: Long,
                                         fieldName: String? = null,
                                         value: String? = null,
                                         cls: Class<T>): Single<T>
            = Single.create { emitter ->
        val realm = getRealm()
        var result: T? = null
        if (type == SYNC) {
            realm.executeTransaction({ realm1 ->
                                         result = realm.where(cls)
                                                 .equalTo(fieldName, value)
                                                 .findFirst()
                                         if (isNotNullObject(emitter, result)) {
                                             val copy = realm1.copyFromRealm(result!!)
                                             result?.deleteFromRealm()
                                             emitter.onSuccess(copy)
                                         }
                                     })
        } else if (type == ASYNC) {
            result = realm.where(cls)
                    .equalTo(fieldName, value)
                    .findFirstAsync()
            if (isNotNullObject(emitter, result)) {
                result?.addChangeListener(RealmObjectChangeListener<T> { o, changeSet ->
                    if (isValid(emitter, result)) {
                        realm.executeTransaction({
                                                     val copy = realm.copyFromRealm(result!!)
                                                     result?.deleteFromRealm()
                                                     emitter.onSuccess(copy)
                                                 })
                    }
                })
            }
        }
        emitter.setDisposable(Disposables.fromRunnable {
            result?.removeAllChangeListeners()
            close(realm)
            if (Looper.getMainLooper() != looper) {
                looper.thread.interrupt()
            }
        })
    }

    private fun close(realm: Realm) {
        if (!realm.isClosed && !realm.isInTransaction) {
            realm.close()
        }
    }

    private fun <T : RealmObject> isValid(emitter: SingleEmitter<T>, obj: T?): Boolean {
        if (obj?.isValid == false) {
            emitter.onError(IllegalArgumentException(
                    "Object is invalid."))
            return false
        }
        return true
    }

    private fun <T : RealmObject> isValid(emitter: SingleEmitter<List<T>>, obj: RealmResults<T>?): Boolean {
        if (obj?.isValid == false) {
            emitter.onError(IllegalArgumentException(
                    "Object is invalid."))
            return false
        }
        return true
    }

    private fun <T : RealmObject> isNotNullObject(emitter: SingleEmitter<T>, obj: T?): Boolean {
        if (obj == null) {
            emitter.onError(IllegalStateException(
                    "Object is no longer managed by Realm. Has it been deleted?"))
            return false
        }
        return true
    }

}