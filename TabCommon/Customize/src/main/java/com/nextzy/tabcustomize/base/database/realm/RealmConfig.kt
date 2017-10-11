package com.nextzy.tabcustomize.base.database.realm

import android.content.Context
import android.content.pm.PackageManager

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmFileException

object RealmConfig {

    private lateinit var config: RealmConfiguration

    fun init(context: Context,
             module: Any,
             version: Int ) {
        Realm.init(context)
        config = RealmConfiguration.Builder()
                .name("nextzy.realm")
                .modules(module)
                .encryptionKey(getRealmEncryptedKey(context))
                .schemaVersion(version.toLong())
                .build()
    }


    fun getRealm(): Realm {
        return try {
            Realm.getInstance(config)
        } catch (exception: RealmFileException) {
            Realm.deleteRealm(config)
            Realm.getInstance(config)
        }
    }


    private fun getRealmEncryptedKey(context: Context): ByteArray {
        val encryptedKey = StringBuilder()
        val valueId = getInstalledTime(context)
        while (encryptedKey.length < RealmConfiguration.KEY_LENGTH) {
            encryptedKey.append(valueId)
            if (encryptedKey.length < RealmConfiguration.KEY_LENGTH) {
                encryptedKey.append("|")
            }
        }
        return encryptedKey.substring(0, RealmConfiguration.KEY_LENGTH).toByteArray()
    }

    private fun getInstalledTime(context: Context): String {
        try {
            return context
                    .packageManager
                    .getPackageInfo(context.packageName, 0).firstInstallTime.toString()
        } catch (ignored: PackageManager.NameNotFoundException) {
        }

        return "N/A"
    }

}
