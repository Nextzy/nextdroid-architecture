package com.nextzy.library.extension

import android.content.Context
import android.telephony.TelephonyManager

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

val Context.isCanUsePhone: Boolean
    get() {
        val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.phoneType != TelephonyManager.PHONE_TYPE_NONE
    }


/**
 * @return
 *
 *  * [TelephonyManager.PHONE_TYPE_NONE] : 0
 *  * [TelephonyManager.PHONE_TYPE_GSM] : 1
 *  * [TelephonyManager.PHONE_TYPE_CDMA] : 2
 *  * [TelephonyManager.PHONE_TYPE_SIP] : 3
 *
 */
val Context.phoneType: Int
    get() {
        val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.phoneType
    }

val Context.isSimCardReady: Boolean
    get() {
        val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.simState == TelephonyManager.SIM_STATE_READY
    }

val Context.simOperatorName: String?
    get() {
        val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.simOperatorName
    }


