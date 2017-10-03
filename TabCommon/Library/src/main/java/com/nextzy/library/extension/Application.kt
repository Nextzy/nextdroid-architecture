package com.nextzy.library.extension

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.graphics.drawable.Drawable

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

fun Application.getAppName(packageName: String = this.packageName): String? {
    if (isSpace(packageName)) return null
    return this.getApplicationInfo(packageName)?.loadLabel(this.packageManager)?.toString()
}

fun Application.getAppIcon(packageName: String = this.packageName): Drawable? {
    if (isSpace(packageName)) return null
    return this.getApplicationInfo(packageName)?.loadIcon(this.packageManager)
}

fun Application.getAppPath(packageName: String = this.packageName): String? {
    if (isSpace(packageName)) return null
    return this.getApplicationInfo(packageName)?.sourceDir
}

fun Application.getVersionName(packageName: String = this.packageName): String? {
    if (isSpace(packageName)) return null
    return this.getPackageInfo(packageName)?.versionName
}

fun Application.getVersionCode(packageName: String = this.packageName): Int? {
    if (isSpace(packageName)) return null
    return this.getPackageInfo(packageName)?.versionCode
}

/** Despite its name, the contents of PackageInfo.signatures is the public keys your app is signed with.
 *  This absolutely, positively does not change between builds.
 *  This is the pure identify of the developer of the app.
 **/
fun Application.getAppSignature(packageName: String = this.packageName): Array<Signature>? {
    if (isSpace(packageName)) return null
    return this.getPackageInfo(packageName)?.signatures
}


fun Application.getAppInfo(packageName: String = this.packageName): AppInfo? {
    return try {
        val pm = this.packageManager
        val pi = pm.getPackageInfo(packageName, 0)
        getBean(pm, pi)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }

}


/* =========================== Private method =================================================== */
private fun Application.getApplicationInfo(packageName: String): ApplicationInfo? {
    return this.getPackageInfo(packageName)?.applicationInfo
}

private fun Application.getPackageInfo(packageName: String): PackageInfo? {
    return try {
        val pm = this.packageManager
        pm.getPackageInfo(packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
}

private fun isSpace(s: String?): Boolean {
    if (s == null) return true
    var i = 0
    val len = s.length
    while (i < len) {
        if (!Character.isWhitespace(s[i])) {
            return false
        }
        ++i
    }
    return true
}

private fun getBean(pm: PackageManager?, pi: PackageInfo?): AppInfo? {
    if (pm == null || pi == null) return null
    val ai = pi.applicationInfo
    val packageName = pi.packageName
    val name = ai.loadLabel(pm).toString()
    val icon = ai.loadIcon(pm)
    val packagePath = ai.sourceDir
    val versionName = pi.versionName
    val versionCode = pi.versionCode
    val isSystem = ApplicationInfo.FLAG_SYSTEM and ai.flags != 0
    return AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem)
}

data class AppInfo (val packageName: String,
                    val name: String,
                    val icon: Drawable,
                    val packagePath: String,
                    val versionName: String,
                    val versionCode: Int,
                    val isSystem: Boolean) {
}