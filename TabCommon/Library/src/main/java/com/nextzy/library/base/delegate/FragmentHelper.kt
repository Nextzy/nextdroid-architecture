package com.nextzy.library.base.delegate

import android.support.v4.app.Fragment

/**
 * Created by thekhaeng on 3/11/2017 AD.
 */

interface FragmentHelper {

    val currentFragment: Fragment?

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
