package com.nextzy.tabcustomize.base.repository.database.realm


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import com.google.gson.Gson
import com.nextzy.library.base.mvvm.layer3Repository.database.realm.BaseLiveDataRealmDatabase
import com.nextzy.library.base.view.holder.base.BaseItem
import com.nextzy.tabcustomize.base.repository.database.realm.model.DefaultRealmObject
import io.reactivex.Single
import io.realm.Realm

class CustomDatabase private constructor() : BaseLiveDataRealmDatabase() {

    private object Holder {
        val INSTANCE = CustomDatabase()
    }

    companion object {
        val DATABASE_VERSION = 1 //update: 9/10/2017
        val PICTURE_LIST = 100
        val PICTURE_BEFORE_ID = 101
        val PICTURE_AFTER_ID = 102

        val instance: CustomDatabase by lazy { Holder.INSTANCE }
    }


    fun initDatabase(context: Context) {
        RealmConfig.init(context,
                         CustomDatabaseModule(),
                         DATABASE_VERSION)
    }

    override
    fun getRealm(): Realm = RealmConfig.getRealm()


    fun <T: BaseItem> saveBaseItemAsSingle(obj: T): Single<T> {
        val json = Gson().toJson(obj)
        val realmObject = DefaultRealmObject(
                obj.id.toString(),
                obj.javaClass.simpleName,
                json)
        return save(realmObject)
                .map { obj }
    }

    fun <T: BaseItem> loadObjectAsLiveData(id:Int, clazz:Class<T>): LiveData<T> {
        return Transformations.map(queryAsLiveData(fieldName = DefaultRealmObject.KEY_ID,
                                                   value = id.toString(),
                                                   realmClass = DefaultRealmObject::class.java))
        { realmObject: DefaultRealmObject ->
            if (realmObject.isValid) {
                Gson().fromJson(realmObject.json, clazz)
            }else{
                null
            }
        }
    }

    fun clearAllData(){
        return deleteAllDatabase()
    }


    fun <T: BaseItem> deleteBaseItemAsSingle(obj: T): Single<T> {
        return delete(DefaultRealmObject.KEY_ID,
                           obj.id.toString(),
                           DefaultRealmObject::class.java)
                .map { defaultRealmObjects ->
                    Gson().fromJson(
                            defaultRealmObjects.json, obj::class.java)
                }

    }


}
