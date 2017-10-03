package th.co.thekhaeng.waterlibrary.kotlin.template.mvvm.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

import com.nextzy.library.customize.mvvm.adapter.CustomMvvmPagerAdapter
import com.nextzy.library.template.mvvm.adapter.KotlinMvvmCustomAdapterViewModel

/**
* Created by「 The Khaeng 」on 20 Aug 2017 :)
*/

class KotlinMvvmCustomPagerAdapter : CustomMvvmPagerAdapter<KotlinMvvmCustomAdapterViewModel> {

    companion object {
        private val TAG = KotlinMvvmCustomPagerAdapter::class.java.simpleName
    }

    constructor(activity: FragmentActivity) : super(activity) {}

    constructor(fragment: Fragment) : super(fragment) {}

    override
    fun setupViewModel(): Class<KotlinMvvmCustomAdapterViewModel>
            = KotlinMvvmCustomAdapterViewModel::class.java

    override
    fun getCount(): Int = 0

    override
    fun getItem(position: Int): Fragment? {
        return null
    }

}
