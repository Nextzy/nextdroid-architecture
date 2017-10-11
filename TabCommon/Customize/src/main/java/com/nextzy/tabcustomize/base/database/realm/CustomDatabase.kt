package com.nextzy.tabcustomize.base.database.realm


import android.content.Context
import com.google.gson.Gson
import com.nextzy.library.base.mvvm.layer3Repository.database.realm.BaseRealmDatabase
import com.nextzy.tabcustomize.base.database.realm.model.CustomModel
import com.nextzy.tabcustomize.base.database.realm.model.DefaultRealmObject
import io.reactivex.Single
import io.realm.Realm
import java.util.*

class CustomDatabase private constructor() : BaseRealmDatabase() {

    private object Holder {
        val INSTANCE = CustomDatabase()
    }

    companion object {
        val DATABASE_VERSION = 1 //update: 9/10/2017

        val instance: CustomDatabase by lazy { Holder.INSTANCE }
    }


    fun initDatabase(context: Context) {
        RealmConfig.init(context,
                         CustomDatabaseModule(),
                         DATABASE_VERSION)
    }

    override
    fun getRealm(): Realm = RealmConfig.getRealm()


    fun saveCustomModel(bottleList: CustomModel): Single<CustomModel> {
        if (bottleList.id == null) bottleList.id = UUID.randomUUID().toString()
        val json = Gson().toJson(bottleList)
        val realmObject = DefaultRealmObject(
                bottleList.id,
                bottleList.javaClass.simpleName,
                json)
        return saveAsync(realmObject)
                .map { bottleList }
    }


    fun loadAllCustomModel(): Single<MutableList<CustomModel>> {
        return queryAllAsync(fieldName = arrayListOf(DefaultRealmObject.KEY_ID),
                             realmClass = DefaultRealmObject::class.java)
                .map { defaultRealmObjects ->
                    val list = defaultRealmObjects.indices.mapTo(ArrayList<CustomModel>()) {
                        Gson().fromJson(
                                defaultRealmObjects[it].json, CustomModel::class.java)
                    }
                    list
                }
    }


    fun deleteCustomModel(bottleList: CustomModel): Single<CustomModel> {
        return deleteAsync(DefaultRealmObject.KEY_ID,
                           bottleList.id,
                           DefaultRealmObject::class.java)
                .map { defaultRealmObjects ->
                    Gson().fromJson(
                            defaultRealmObjects.json, CustomModel::class.java)
                }

    }

}
