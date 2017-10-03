package com.nextzy.library.base.utils

import com.orhanobut.hawk.Hawk
import java.util.*

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

object HawkPreference {

    val KEY_DATA_1 = "key_data_1"
    val KEY_DATA_2 = "key_data_3"
    val KEY_DATA_3 = "key_data_2"


    private val SEPARATE = "_"


    fun saveData(data1: String,
                 data2: String,
                 data3: String) {
        Hawk.put(KEY_DATA_1, data1)
        Hawk.put(KEY_DATA_2, data2)
        Hawk.put(KEY_DATA_3, data3)
    }


    fun loadData(): Map<String, String> {
        val result = HashMap<String, String>()
        result.put(KEY_DATA_1, Hawk.get<Any>(KEY_DATA_1, null) as String)
        result.put(KEY_DATA_2, Hawk.get<Any>(KEY_DATA_2, null) as String)
        result.put(KEY_DATA_3, Hawk.get<Any>(KEY_DATA_3, null) as String)
        return result
    }

    fun removeRemove() {
        Hawk.delete(KEY_DATA_1)
        Hawk.delete(KEY_DATA_1)
        Hawk.delete(KEY_DATA_1)
    }
}
