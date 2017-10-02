package com.nextzy.library.base.mvvm.layer3Repository.database;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by「 The Khaeng 」on 28 Aug 2017 :)
 */
public class DefaultRealmObject extends RealmObject{
    public static final String KEY_ID = "objectId";
    public static final String KEY_CLASS = "objectClass";
    public static final String KEY_JSON = "jsonDataResult";

    @PrimaryKey
    String objectId;
    String objectClass;
    String jsonDataResult;

    public DefaultRealmObject(){
    }

    public DefaultRealmObject( String objectClass,
                               String json ){
        this.objectId = UUID.randomUUID().toString();
        this.objectClass = objectClass;
        this.jsonDataResult = json;
    }

    public DefaultRealmObject(String objectId,
                              String objectClass,
                              String json ){
        this.objectId = objectId;
        this.objectClass = objectClass;
        this.jsonDataResult = json;
    }

    public String getObjectId(){
        return objectId;
    }

    public String getObject(){
        return objectClass;
    }

    public String getJson(){
        return jsonDataResult;
    }
}
