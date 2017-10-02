package com.nextzy.library.base.delegate

import android.annotation.SuppressLint
import android.support.annotation.AnimRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction

/**
 * Created by thekhaeng on 5/25/2017 AD.
 */

class OpenFragmentTransaction(
        private var isFinish: Boolean = false,
        private var enter: Int = NO_ASSIGN,
        private var exit: Int = NO_ASSIGN,
        private var popEnter: Int = NO_ASSIGN,
        private var popExit: Int = NO_ASSIGN,
        private var containerLayoutRes: Int = NO_ASSIGN) {

    companion object {
        private const val NO_ASSIGN = -1
    }

    fun setFinish(isFinish: Boolean): OpenFragmentTransaction {
        this.isFinish = isFinish
        return this
    }

    fun setAnim(@AnimRes enter: Int, @AnimRes exit: Int): OpenFragmentTransaction {
        this.enter = enter
        this.exit = exit
        return this
    }

    fun setPopAnim(@AnimRes popEnter: Int, @AnimRes popExit: Int): OpenFragmentTransaction {
        this.popEnter = popEnter
        this.popExit = popExit
        return this

    }

    fun setContainerLayout(@LayoutRes layout: Int): OpenFragmentTransaction {
        this.containerLayoutRes = layout
        return this
    }

    @SuppressLint("CommitTransaction")
    fun open(activity: FragmentActivity, fragment: Fragment) {
        if (containerLayoutRes == NO_ASSIGN)
            throw RuntimeException("setContainerLayout(layout) before open.")
        val tag = fragment.javaClass.simpleName
        val transaction = getDefaultFragmentTransaction(activity)
                .replace(containerLayoutRes, fragment, tag)
        if (!isFinish) transaction.addToBackStack(tag)
        transaction.commit()
    }

    @SuppressLint("CommitTransaction")
    fun add(activity: FragmentActivity, fragment: Fragment) {
        if (containerLayoutRes == NO_ASSIGN)
            throw RuntimeException("setContainerLayout(layout) before open.")
        val tag = fragment.javaClass.simpleName
        val transaction = getDefaultFragmentTransaction(activity)
                .add(containerLayoutRes, fragment, tag)
        if (!isFinish) transaction.addToBackStack(tag)
        transaction.commit()
    }

    /* =========================== Private method ============================================= **/
    @SuppressLint("CommitTransaction")
    private fun getDefaultFragmentTransaction(activity: FragmentActivity): FragmentTransaction {
        val transaction = activity.supportFragmentManager
                .beginTransaction()
        if (enter != NO_ASSIGN && exit != NO_ASSIGN && popEnter != NO_ASSIGN && popExit != NO_ASSIGN) {
            transaction.setCustomAnimations(
                    enter, exit,
                    popEnter, popExit)
        }
        return transaction
    }

}
