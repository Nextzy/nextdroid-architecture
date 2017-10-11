package com.nextzy.example

import android.content.Context
import com.google.gson.Gson
import com.nextzy.library.base.mvvm.layer3Repository.database.realm.BaseRealmDatabase
import com.nextzy.tabcustomize.base.database.realm.CustomDatabaseModule
import com.nextzy.tabcustomize.base.database.realm.RealmConfig
import com.nextzy.tabcustomize.base.database.realm.model.DefaultRealmObject
import io.reactivex.Single
import io.realm.Realm
import java.util.*

class CustomDatabase : BaseRealmDatabase() {

    private object Holder {
        val INSTANCE = CustomDatabase()
    }

    private val random = Random()

    companion object {
        val DATABASE_VERSION = 1 //update: 7/13/2017

        val instance: CustomDatabase by lazy { Holder.INSTANCE }
    }

    override
    fun getRealm(): Realm = RealmConfig.getRealm()


    fun initDatabase(context: Context) {
        RealmConfig.init(context,
                         CustomDatabaseModule(),
                         DATABASE_VERSION)
    }

    fun saveMockObject(mode: Long, obj: MockObject): Single<MockObject> {
        if (obj.id == null) obj.id = randInt()
        val json = Gson().toJson(obj)
        val realmObject = DefaultRealmObject(
                obj.id,
                obj.javaClass.simpleName,
                json)
        return if (mode == BaseRealmDatabase.ASYNC) {
            saveAsync(realmObject)
                    .map { obj }
        } else {
            save(realmObject)
                    .map { obj }
        }
    }

    fun deleteMockObject(mode: Long, id: String): Single<MockObject> {
        if (mode == BaseRealmDatabase.ASYNC) {
            return deleteAsync(DefaultRealmObject.KEY_ID,
                               id,
                               DefaultRealmObject::class.java)
                    .map { defaultRealmObjects ->
                        Gson().fromJson(
                                defaultRealmObjects.json, MockObject::class.java)
                    }
        } else {
            return delete(DefaultRealmObject.KEY_ID,
                          id,
                          DefaultRealmObject::class.java)
                    .map { defaultRealmObjects ->
                        Gson().fromJson(
                                defaultRealmObjects.json, MockObject::class.java)
                    }
        }
    }

    fun findMockObject(mode: Long, id: String): Single<MockObject> {
        if (mode == BaseRealmDatabase.ASYNC) {
            return queryAsync(fieldName = DefaultRealmObject.KEY_ID,
                              value = id,
                              realmClass = DefaultRealmObject::class.java)
                    .map { defaultRealmObject ->
                        Gson().fromJson(
                                defaultRealmObject.json, MockObject::class.java)

                    }
        } else {
            return query(fieldName = DefaultRealmObject.KEY_ID,
                         value = id,
                         realmClass = DefaultRealmObject::class.java)
                    .map { defaultRealmObject ->
                        Gson().fromJson(
                                defaultRealmObject.json, MockObject::class.java)
                    }
        }

    }

    fun findAllMockObject(mode: Long): Single<MutableList<MockObject>> {
        if (mode == BaseRealmDatabase.ASYNC) {
            return queryAllAsync(fieldName = arrayListOf(DefaultRealmObject.KEY_ID),
                                 realmClass = DefaultRealmObject::class.java)
                    .map { defaultRealmObject ->
                        val list = defaultRealmObject.indices.mapTo(ArrayList<MockObject>()) {
                            Gson().fromJson(
                                    defaultRealmObject[it].json, MockObject::class.java)
                        }
                        list
                    }
        } else {
            return queryAll(fieldName = arrayListOf(DefaultRealmObject.KEY_ID),
                            realmClass = DefaultRealmObject::class.java)
                    .map { defaultRealmObject ->
                        val list = defaultRealmObject.indices.mapTo(ArrayList<MockObject>()) {
                            Gson().fromJson(
                                    defaultRealmObject[it].json, MockObject::class.java)
                        }
                        list
                    }
        }
    }

    private fun randInt(): String {
        return random.nextInt(100).toString()
    }
}