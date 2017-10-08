package com.nextzy.tabcustomize.template


import android.os.Bundle
import com.nextzy.tabcustomize.base.CustomFragment


/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

class KotlinCustomFragment : CustomFragment() {

    companion object {
        fun newInstance(): KotlinCustomFragment {
            val fragment = KotlinCustomFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override
    fun setupLayoutView(): Int = 0

    override
    fun setupInstance() {

    }

    override
    fun setupView() {

    }

    override
    fun initialize() {

    }

    override
    fun onRestoreArgument(bundle: Bundle) {
        super.onRestoreArgument(bundle)
    }

    override
    fun onRestoreView(savedInstanceState: Bundle) {
        super.onRestoreView(savedInstanceState)
    }

    override
    fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

}

