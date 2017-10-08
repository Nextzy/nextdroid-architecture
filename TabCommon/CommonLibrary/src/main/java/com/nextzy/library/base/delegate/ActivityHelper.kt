package com.nextzy.library.base.delegate

import android.os.Bundle

/**
 * Created by「 The Khaeng 」on 31 Aug 2017 :)
 */

interface ActivityHelper {

    fun openActivity(targetClass: Class<*>,
                     request: Int= -1,
                     data: Bundle? = null)

    fun openActivityWithFinish(targetClass: Class<*>,
                               data: Bundle? = null)

    fun openActivityWithAllFinish(targetClass: Class<*>,
                                  data: Bundle? = null)

}
