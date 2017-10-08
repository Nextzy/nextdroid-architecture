package com.nextzy.library.base.utils.permission

import java.util.*

/**
 * Created by TheKhaeng.
 */

class PermissionResult(
        private var permissionList: List<Permission>) {

    private val isPermissionListAvailable: Boolean
        get() = permissionList.isNotEmpty()


    val isAnyPermissionPermanentlyDenied: Boolean
        get() {
            if (isPermissionListAvailable) {
                return permissionList.any { it.isDenied }
            }
            return false
        }


    val deniedPermissionResponses: List<Permission>
        get() {
            val deniedPermissionList = ArrayList<Permission>()
            if (isPermissionListAvailable) {
                permissionList.filterTo(deniedPermissionList) { it.isDenied }
            }
            return deniedPermissionList
        }

    val grantedPermissionResponses: List<Permission>
        get() {
            val grantedPermissionList = ArrayList<Permission>()
            if (isPermissionListAvailable) {
                permissionList.filterTo(grantedPermissionList) { it.isGranted }
            }
            return grantedPermissionList
        }


    fun areAllPermissionsGranted(): Boolean {
        if (isPermissionListAvailable) {
            return permissionList.none { it.isDenied }
        }
        return false
    }

    class Permission(permissionName: String,
                     isGranted: Boolean) {
        private var permissionName: String? = null
        var isGranted: Boolean = false

        val isDenied: Boolean
            get() = !isGranted
    }
}
