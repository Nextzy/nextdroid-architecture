package com.nextzy.library.base.delegate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.view.View
import com.nextzy.library.extension.notnull

/**
 * Created by「 The Khaeng 」on 31 Aug 2017 :)
 */

class OpenActivityTransaction(
        private var isFinish: Boolean = false,
        private var bundle: Bundle? = null,
        private var requestCode: Int = NO_ASSIGN,
        private var isFinishAll: Boolean = false,
        private var enterAnimId: Int = NO_ASSIGN,
        private var exitAnimId: Int = NO_ASSIGN,
        private var sharedElement: Array<out Pair<View, String>>? = null) {


    companion object {
        private const val NO_ASSIGN = -1
        const val KEY_FINISH = "key_finish"
    }


    fun setRequestCode(requestCode: Int): OpenActivityTransaction {
        this.requestCode = requestCode
        return this
    }

    fun setBundle(bundle: Bundle?): OpenActivityTransaction {
        this.bundle = bundle
        return this
    }

    fun setFinish(isFinish: Boolean): OpenActivityTransaction {
        this.isFinish = isFinish
        return this
    }

    fun setFinishAllPrevious(isFinishAll: Boolean): OpenActivityTransaction {
        this.isFinishAll = isFinishAll
        return this
    }

    fun setAnim(@AnimRes enterAnimId: Int, @AnimRes exitAnimId: Int): OpenActivityTransaction {
        this.enterAnimId = enterAnimId
        this.exitAnimId = exitAnimId
        return this
    }

    @SafeVarargs
    fun setPairTransition(vararg sharedElement: Pair<View, String>): OpenActivityTransaction {
        this.sharedElement = sharedElement
        return this
    }

    fun open(activity: Activity?, targetClass: Class<*>) {
        activity?.apply {
            val intent = Intent(activity, targetClass)
            bundle?.let { intent.putExtras(it) }
            if (enterAnimId != NO_ASSIGN || exitAnimId != NO_ASSIGN) {
                intent.putExtra(KEY_FINISH, isFinish || isFinishAll)
            }

            var options: ActivityOptionsCompat? = null
            sharedElement?.let {
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        *sharedElement!!)
            }

            if (requestCode == NO_ASSIGN) {
                startActivity(intent, options?.toBundle())
            } else {
                startActivityForResult(intent, requestCode, options?.toBundle())
            }

            if (isFinish && !isFinishAll) finish()
            if (isFinishAll) ActivityCompat.finishAffinity(this)

            if (enterAnimId != NO_ASSIGN || exitAnimId != NO_ASSIGN) {
                overridePendingTransition(enterAnimId, exitAnimId)
            }

            sharedElement = null
            bundle = null
        }
    }


    fun open(fragment: Fragment?, targetClass: Class<*>) {
        fragment?.apply {
            val intent = Intent(context, targetClass)
            bundle?.let { intent.putExtras(it) }
            if (enterAnimId != NO_ASSIGN || exitAnimId != NO_ASSIGN) {
                intent.putExtra(KEY_FINISH, isFinish || isFinishAll)
            }

            var options: ActivityOptionsCompat? = null

            kotlin.Pair(sharedElement, activity).notnull { sharedElement, activity ->
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        *sharedElement)

            }

            if (requestCode == NO_ASSIGN) {
                startActivity(intent, options?.toBundle())
            } else {
                startActivityForResult(intent, requestCode, options?.toBundle())
            }

            if (isFinish && !isFinishAll) fragment.activity?.finish()
            if (isFinishAll) fragment.activity?.apply { ActivityCompat.finishAffinity(this) }

            if (enterAnimId != NO_ASSIGN || exitAnimId != NO_ASSIGN) {
                activity?.overridePendingTransition(enterAnimId, exitAnimId)
            }


            sharedElement = null
            bundle = null
        }
    }

}
