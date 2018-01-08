package com.nextzy.tabcustomize.base.extension

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.nextzy.tabcustomize.dialog.DefaultDialogFragment
import com.nextzy.tabcustomize.dialog.DialogManager
import com.nextzy.tabcustomize.dialog.listener.DefaultDialogClickListener
import com.nextzy.tabcustomize.dialog.listener.DialogDismissListener

/**
 * Created by「 The Khaeng 」on 21 Oct 2017 :)
 */

// copy paste
//fun FragmentActivity.showLoading() = DialogManager.
//fun Fragment.showLoading() = DialogManager.


fun FragmentActivity.showLoading() = DialogManager.showLoadingDialog(this.supportFragmentManager)
fun Fragment.showLoading() = this.fragmentManager?.let { DialogManager.showLoadingDialog(it) }


fun FragmentActivity.showDialog(dialog: DialogFragment) = DialogManager.showDialog(this.supportFragmentManager, dialog)
fun Fragment.showDialog(dialog: DialogFragment) = this.fragmentManager?.let { DialogManager.showDialog(it, dialog) }


fun FragmentActivity.hideLoading() = DialogManager.dismissDialog()
fun Fragment.hideLoading() = DialogManager.dismissDialog()


fun FragmentActivity.showPositiveDialog(
        @DrawableRes iconResId: Int = -1,
        @StringRes title: Int = -1,
        @StringRes body: Int = -1,
        @StringRes button: Int = -1,
        data: Bundle? = null,
        tag: String? = null,
        clickListener: DefaultDialogClickListener? = null,
        dismissListener: DialogDismissListener? = null
                                       )
        = DialogManager.showDefaultDialog(this.supportFragmentManager, DefaultDialogFragment.POSITIVE, iconResId, title, body, button, data, tag, clickListener, dismissListener)
fun Fragment.showPositiveDialog(
        @DrawableRes iconResId: Int = -1,
        @StringRes title: Int = -1,
        @StringRes body: Int = -1,
        @StringRes button: Int = -1,
        data: Bundle? = null,
        tag: String? = null,
        clickListener: DefaultDialogClickListener? = null,
        dismissListener: DialogDismissListener? = null)
        = this.fragmentManager?.let { DialogManager.showDefaultDialog(it, DefaultDialogFragment.POSITIVE, iconResId, title, body, button, data, tag, clickListener, dismissListener) }


fun FragmentActivity.showNegativeRedDialog(
        @DrawableRes iconResId: Int = -1,
        @StringRes title: Int = -1,
        @StringRes body: Int = -1,
        @StringRes button: Int = -1,
        data: Bundle? = null,
        tag: String? = null,
        clickListener: DefaultDialogClickListener? = null,
        dismissListener: DialogDismissListener? = null
                                       )
        = DialogManager.showDefaultDialog(this.supportFragmentManager, DefaultDialogFragment.NEGATIVE_RED, iconResId, title, body, button, data, tag, clickListener, dismissListener)
fun Fragment.showNegativeDialog(
        @DrawableRes iconResId: Int = -1,
        @StringRes title: Int = -1,
        @StringRes body: Int = -1,
        @StringRes button: Int = -1,
        data: Bundle? = null,
        tag: String? = null,
        clickListener: DefaultDialogClickListener? = null,
        dismissListener: DialogDismissListener? = null)
        = this.fragmentManager?.let { DialogManager.showDefaultDialog(it, DefaultDialogFragment.NEGATIVE_RED, iconResId, title, body, button, data, tag, clickListener, dismissListener) }


fun FragmentActivity.showNegativeTransparentDialog(
        @DrawableRes iconResId: Int = -1,
        @StringRes title: Int = -1,
        @StringRes body: Int = -1,
        @StringRes button: Int = -1,
        data: Bundle? = null,
        tag: String? = null,
        clickListener: DefaultDialogClickListener? = null,
        dismissListener: DialogDismissListener? = null
                                          )
        = DialogManager.showDefaultDialog(this.supportFragmentManager, DefaultDialogFragment.NEGATIVE_TRANSPARENT, iconResId, title, body, button, data, tag, clickListener, dismissListener)
fun Fragment.showNegativeTransparentDialog(
        @DrawableRes iconResId: Int = -1,
        @StringRes title: Int = -1,
        @StringRes body: Int = -1,
        @StringRes button: Int = -1,
        data: Bundle? = null,
        tag: String? = null,
        clickListener: DefaultDialogClickListener? = null,
        dismissListener: DialogDismissListener? = null)
        = this.fragmentManager?.let { DialogManager.showDefaultDialog(it, DefaultDialogFragment.NEGATIVE_TRANSPARENT, iconResId, title, body, button, data, tag, clickListener, dismissListener) }