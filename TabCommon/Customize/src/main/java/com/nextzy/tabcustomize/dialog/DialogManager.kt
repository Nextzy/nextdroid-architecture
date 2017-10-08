package com.nextzy.tabcustomize.dialog

import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

object DialogManager {

    private val TAG = DialogManager::class.java.simpleName


    private var dialog: DialogFragment? = null

    fun showLoadingDialog(fragmentManager: FragmentManager) {
        dismissDialog()
        dialog = LoadingDialog.Builder()
                .build()
        dialog?.show(fragmentManager, TAG)
    }

    fun dismissDialog() {
        if (dialog?.isAdded == true) {
            dialog?.dismiss()
            dialog = null
        }
    }

}
