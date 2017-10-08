package com.nextzy.library.base.utils.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.*

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

object PermissionUtils {

    interface PermissionCallback {
        fun onPermissionResult(permissionResult: PermissionResult)
    }

    fun isGrantPermission(context: Context, permission: String): Boolean {
        val res = context.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(activity: Activity, callback: PermissionCallback?, vararg permissions: String) {
        try {
            Dexter.withActivity(activity)
                    .withPermissions(*permissions)
                    .withListener(object : MultiplePermissionsListener {
                        override
                        fun onPermissionsChecked(report: MultiplePermissionsReport) {
                            if (callback != null) {
                                val permissionList = ArrayList<PermissionResult.Permission>()
                                permissionList.addAll(getGrantedPermissionList(report.grantedPermissionResponses))
                                permissionList.addAll(getDeniedPermissionList(report.deniedPermissionResponses))
                                val permissionResult = PermissionResult(permissionList)
                                callback.onPermissionResult(permissionResult)
                            }
                        }

                        override
                        fun onPermissionRationaleShouldBeShown(permissions: List<com.karumi.dexter.listener.PermissionRequest>,
                                                               token: PermissionToken) {
                            token.continuePermissionRequest()
                        }
                    }).check()
        } catch (exception: NullPointerException) {
            throw NullPointerException("Application class should be extend from Application class before use Permission class.")
        }

    }

    private fun getGrantedPermissionList(permissionGrantedResponseList: List<PermissionGrantedResponse>?): List<PermissionResult.Permission> {
        val permissionList = ArrayList<PermissionResult.Permission>()
        if (permissionGrantedResponseList?.isNotEmpty() == true) {
            permissionGrantedResponseList
                    .map { it.permissionName }
                    .mapTo(permissionList) { PermissionResult.Permission(it, true) }
        }
        return permissionList
    }

    private fun getDeniedPermissionList(permissionDeniedResponseList: List<PermissionDeniedResponse>?): List<PermissionResult.Permission> {
        val permissionList = ArrayList<PermissionResult.Permission>()
        if (permissionDeniedResponseList?.isNotEmpty() == true) {
            permissionDeniedResponseList
                    .map { it.permissionName }
                    .mapTo(permissionList) { PermissionResult.Permission(it, false) }
        }
        return permissionList
    }

}
