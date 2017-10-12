package com.nextzy.tabcustomize.base.repository.database.realm


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import com.google.gson.Gson
import com.nextzy.library.base.mvvm.layer3Repository.database.realm.BaseLiveDataRealmDatabase
import com.nextzy.tabcustomize.base.repository.database.realm.model.DefaultRealmObject
import com.nextzy.tabcustomize.template.mvvm.repository.model.CustomModel
import io.reactivex.Single
import io.realm.Realm
import java.util.*

class CustomDatabase private constructor() : BaseLiveDataRealmDatabase() {

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


    fun saveCustomModel(obj: CustomModel): Single<CustomModel> {
        if (obj.id == null) obj.id = UUID.randomUUID().toString()
        val json = Gson().toJson(obj)
        val realmObject = DefaultRealmObject(
                obj.id,
                obj.javaClass.simpleName,
                json)
        return save(realmObject)
                .map { obj }
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


    fun loadCustomModelAsLiveData(id: Int): LiveData<CustomModel> {
        return Transformations.map(queryAsLiveData(fieldName = DefaultRealmObject.KEY_ID,
                                                   value = id.toString(),
                                                   realmClass = DefaultRealmObject::class.java))
        { realmObject: DefaultRealmObject ->
            Gson().fromJson(realmObject.json, CustomModel::class.java)
        }
    }

    fun loadAllCustomModelAsLiveData(): LiveData<MutableList<CustomModel>> {
        return Transformations.map(queryAllAsLiveData(fieldName = arrayListOf(DefaultRealmObject.KEY_ID),
                                                      realmClass = DefaultRealmObject::class.java))
        { realmObject: List<DefaultRealmObject> ->
            val list = realmObject.indices.mapTo(ArrayList<CustomModel>()) {
                Gson().fromJson(
                        realmObject[it].json, CustomModel::class.java)
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
