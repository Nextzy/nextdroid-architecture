package com.nextzy.library.base.delegate

import android.support.v4.app.Fragment

/**
* Created by「 The Khaeng 」on 08 Oct 2017 :)
*/

interface FragmentHelper {

    fun getCurrentFragment(): Fragment?

    fun bindFragmentContainer(containerLayoutRes: Int)

    fun initFragment(fragment: Fragment)

    fun contain(tag: String): Boolean

    fun clearBackStackFragmentTo(index: Int)

    fun clearAllBackStacksFragment()

    fun backPressedFragment()

    fun backPressedToFragment(tag: String)

    fun openFragmentSlideInRight(fragment: Fragment)

    fun openFragmentSlideInLeft(fragment: Fragment)

    fun openFragment(fragment: Fragment)
}
