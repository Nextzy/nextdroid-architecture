package com.nextzy.library.base.utils;

import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thekhaeng on 1/13/2017 AD.
 */

public class DefaultPreference{

    public static final String KEY_DATA_1 = "key_data_1";
    public static final String KEY_DATA_2 = "key_data_3";
    public static final String KEY_DATA_3 = "key_data_2";

    private static DefaultPreference instance;

    private static final String SEPARATE = "_";

    public static DefaultPreference getInstance(){
        if( instance == null ){
            instance = new DefaultPreference();
        }
        return instance;
    }

    private DefaultPreference(){
    }

    public void saveData( String data1,
                          String data2,
                          String data3 ){
        Hawk.put( KEY_DATA_1, data1 );
        Hawk.put( KEY_DATA_2, data2 );
        Hawk.put( KEY_DATA_3, data3 );
    }


    public Map<String, String> loadData(){
        Map<String, String> result = new HashMap<>();
        result.put( KEY_DATA_1, (String) Hawk.get( KEY_DATA_1, null ) );
        result.put( KEY_DATA_2, (String) Hawk.get( KEY_DATA_2, null ) );
        result.put( KEY_DATA_3, (String) Hawk.get( KEY_DATA_3, null ) );
        return result;
    }

    public void removeRemove(){
        Hawk.delete( KEY_DATA_1 );
        Hawk.delete( KEY_DATA_1 );
        Hawk.delete( KEY_DATA_1 );
    }
}
