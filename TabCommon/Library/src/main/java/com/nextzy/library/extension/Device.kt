package com.nextzy.library.extension

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.wifi.WifiManager
import android.os.storage.StorageManager
import android.provider.Settings
import java.lang.reflect.InvocationTargetException
import java.net.NetworkInterface
import java.util.*

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

val sdkVersion: Int = android.os.Build.VERSION.SDK_INT

val Context.androidID: String
    @SuppressLint("HardwareIds")
    get() = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)


fun Context.getMacAddress(): String {
    var macAddress = getMacAddressByWifiInfo()
    if ("02:00:00:00:00:00" != macAddress) return macAddress

    macAddress = getMacAddressByNetworkInterface()
    if ("02:00:00:00:00:00" != macAddress) return macAddress

    return "please open wifi"
}

fun Context.isSDCardEnable(): Boolean {
    return this.getSDCardPaths().isNotEmpty()
}

fun Context.getSDCardPaths(): List<String> {
    val storageManager = this.getSystemService(Context.STORAGE_SERVICE) as StorageManager
    var paths: List<String> = ArrayList()
    try {
        val getVolumePathsMethod = StorageManager::class.java.getMethod("getVolumePaths")
        getVolumePathsMethod.isAccessible = true
        val invoke = getVolumePathsMethod.invoke(storageManager)
        paths = Arrays.asList(invoke as String)
    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    }

    return paths
}

/* =========================== Private method =================================================== */

/** <p>{@code <uses-permission android:name="android.permission.INTERNET"/>}</p> **/
@SuppressLint("HardwareIds")
private fun Context.getMacAddressByWifiInfo(): String {
    return try {
        val wifi = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        info?.macAddress ?: "02:00:00:00:00:00"
    } catch (e: Exception) {
        e.printStackTrace()
        "02:00:00:00:00:00"
    }
}

private fun getMacAddressByNetworkInterface(): String {
    try {
        val nis = Collections.list<NetworkInterface>(NetworkInterface.getNetworkInterfaces())
        for (ni in nis) {
            if (ni.name.equals("wlan0", ignoreCase = true).not()) continue
            val macBytes = ni.hardwareAddress
            if (macBytes?.isNotEmpty() == true) {
                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02x:", b))
                }
                return res1.deleteCharAt(res1.length - 1).toString()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return "02:00:00:00:00:00"
}
