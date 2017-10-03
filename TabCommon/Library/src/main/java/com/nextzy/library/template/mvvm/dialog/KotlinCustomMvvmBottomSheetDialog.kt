package com.nextzy.library.template.mvvm.dialog


import android.os.Bundle
import android.view.View
import com.nextzy.library.customize.mvvm.dialog.CustomMvvmBottomSheetDialog


/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

class KotlinCustomMvvmBottomSheetDialog
    : CustomMvvmBottomSheetDialog<KotlinCustomDialogViewModel>() {

    companion object {
        @JvmOverloads
        fun create(bundle: Bundle? = null): KotlinCustomMvvmBottomSheetDialog {
            val fragment = KotlinCustomMvvmBottomSheetDialog()
            var args = Bundle()
            if (bundle != null) args = bundle
            fragment.arguments = args
            return fragment
        }
    }


    override
    fun setupViewModel(): Class<KotlinCustomDialogViewModel>
            = KotlinCustomDialogViewModel::class.java

    override
    fun setupLayoutView(): Int = 0

    override
    fun bindView(view: View) {

    }

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
    fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

}

