package com.nextzy.library.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import android.util.Log

import java.lang.reflect.Method
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.net.UnknownHostException
import java.util.Enumeration

import jp.wasabeef.glide.transformations.internal.Utils

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 *
 *`<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
 */

private val NETWORK_TYPE_GSM = 16
private val NETWORK_TYPE_TD_SCDMA = 17
private val NETWORK_TYPE_IWLAN = 18


enum class NetworkType {
    NETWORK_WIFI,
    NETWORK_4G,
    NETWORK_3G,
    NETWORK_2G,
    NETWORK_UNKNOWN,
    NETWORK_NO
}


val Context.isInternetConnected: Boolean
    get() {
        val info = this.activeNetworkInfo
        return info != null && info.isConnected
    }

var Context.isInternetDataEnabled: Boolean
    get() {
        try {
            val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val getMobileDataEnabledMethod = tm.javaClass.getDeclaredMethod("getDataEnabled")
            if (null != getMobileDataEnabledMethod) {
                return getMobileDataEnabledMethod.invoke(tm) as Boolean
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }
    private set(value) {}

val Context.isInternet4G: Boolean
    get() {
        val info = this.activeNetworkInfo
        return info != null && info.isAvailable && info.subtype == TelephonyManager.NETWORK_TYPE_LTE
    }

var Context.isWifiEnabled: Boolean
    get() {
        @SuppressLint("WifiManagerLeak")
        val wifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }
    private set(value) {
    }

val Context.isWifiConnected: Boolean
    get() {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return (cm.activeNetworkInfo != null
                && cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI)
    }




/**
 *
 *  * [Network.NetworkType.NETWORK_WIFI]
 *  * [Network.NetworkType.NETWORK_4G]
 *  * [Network.NetworkType.NETWORK_3G]
 *  * [Network.NetworkType.NETWORK_2G]
 *  * [Network.NetworkType.NETWORK_UNKNOWN]
 *  * [Network.NetworkType.NETWORK_NO]
 *
 */
val Context.networkType: NetworkType
    get() {
        var netType = NetworkType.NETWORK_NO
        val info = this.activeNetworkInfo
        if (info != null && info.isAvailable) {
            when {
                info.type == ConnectivityManager.TYPE_WIFI -> netType = NetworkType.NETWORK_WIFI
                info.type == ConnectivityManager.TYPE_MOBILE -> when (info.subtype) {

                    NETWORK_TYPE_GSM, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> netType = NetworkType.NETWORK_2G

                    NETWORK_TYPE_TD_SCDMA, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> netType = NetworkType.NETWORK_3G

                    NETWORK_TYPE_IWLAN, TelephonyManager.NETWORK_TYPE_LTE -> netType = NetworkType.NETWORK_4G
                    else -> {

                        val subtypeName = info.subtypeName
                        if (subtypeName.equals("TD-SCDMA", ignoreCase = true)
                                || subtypeName.equals("WCDMA", ignoreCase = true)
                                || subtypeName.equals("CDMA2000", ignoreCase = true)) {
                            netType = NetworkType.NETWORK_3G
                        } else {
                            netType = NetworkType.NETWORK_UNKNOWN
                        }
                    }
                }
                else -> netType = NetworkType.NETWORK_UNKNOWN
            }
        }
        return netType
    }



private val Context.activeNetworkInfo: NetworkInfo?
    get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
