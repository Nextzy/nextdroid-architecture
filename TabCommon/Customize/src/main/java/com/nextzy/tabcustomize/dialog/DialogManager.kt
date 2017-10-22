package com.nextzy.tabcustomize.dialog

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import com.nextzy.tabcustomize.dialog.listener.DefaultDialogClickListener
import com.nextzy.tabcustomize.dialog.listener.DialogDismissListener

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

object DialogManager {

    private val TAG = DialogManager::class.java.simpleName
    private val TAG_DIALOG = "tag_dialog"


    private var dialog: DialogFragment? = null

    fun showLoadingDialog(fragmentManager: FragmentManager) {
        dismissDialog()
        dialog = LoadingDialog.Builder()
                .build()
        dialog?.show(fragmentManager, TAG)
    }

    fun showDialog(fragmentManager: FragmentManager, dialog: DialogFragment) {
        dismissDialog()
        dialog.show(fragmentManager, TAG)
    }


    fun showDefaultDialog(
            fragmentManager: FragmentManager,
            @DefaultDialogFragment.Companion.Type type: Long,
            @DrawableRes iconResId: Int = -1,
            @StringRes title: Int = -1,
            @StringRes body: Int = -1,
            @StringRes button: Int = -1,
            data: Bundle? = null,
            tag: String? = null,
            clickListener: DefaultDialogClickListener? = null,
            dismissListener: DialogDismissListener? = null) {
        dismissDialog()
        dialog = DefaultDialogFragment.Builder()
                .setType(type)
                .setIcon(iconResId)
                .setTitle(title)
                .setButton(button)
                .setBody(body)
                .setData(data)
                .setDefalutClickListener(clickListener)
                .setDialogDismissListener(dismissListener)
                .build()
        dialog?.show(fragmentManager, tag ?: TAG_DIALOG)
    }

    fun dismissDialog() {
        if (dialog?.isAdded == true) {
            dialog?.dismiss()
            dialog = null
        }
    }


}
