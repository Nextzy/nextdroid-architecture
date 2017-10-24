package com.nextzy.tabcustomize.template.mvvm.dialog


import android.os.Bundle
import android.view.View
import com.nextzy.tabcustomize.base.mvvm.dialog.CustomMvvmDialogFragment


/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

class KotlinCustomMvvmDialogFragment : CustomMvvmDialogFragment() {

    companion object {
        fun newInstance(): KotlinCustomMvvmDialogFragment {
            val fragment = KotlinCustomMvvmDialogFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: KotlinCustomDialogViewModel

    override
    fun onSetupViewModel() {
        viewModel = getViewModel(KotlinCustomDialogViewModel::class.java)
    }


    override
    fun setupLayoutView(): Int = 0

    override
    fun onBindView(view: View?) {
        super.onBindView(view)
    }

    override
    fun onSetupInstance() {
        super.onSetupInstance()
    }

    override
    fun onSetupView() {
        super.onSetupView()
    }

    override
    fun onPrepareInstance() {
        super.onPrepareInstance()
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


    class Builder {
        fun build(): KotlinCustomMvvmDialogFragment {
            return newInstance()
        }
    }


}

