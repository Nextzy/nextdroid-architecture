package com.nextzy.tabcustomize.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.IntDef
import android.support.annotation.StringRes
import android.view.View
import com.nextzy.library.extension.view.show
import com.nextzy.tabcustomize.R
import com.nextzy.tabcustomize.base.mvvm.dialog.CustomMvvmDialogFragment
import com.nextzy.tabcustomize.dialog.listener.DefaultDialogClickListener
import com.nextzy.tabcustomize.dialog.listener.DialogDismissListener
import kotlinx.android.synthetic.main.fragment_dialog_default.btn_confirm as btnConfirm
import kotlinx.android.synthetic.main.fragment_dialog_default.ic_cancel as icCancel
import kotlinx.android.synthetic.main.fragment_dialog_default.ic_dialog as icon
import kotlinx.android.synthetic.main.fragment_dialog_default.tv_body as tvBody
import kotlinx.android.synthetic.main.fragment_dialog_default.tv_title as tvTitle


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */


class DefaultDialogFragment : CustomMvvmDialogFragment() {

    companion object {
        const val POSITIVE = 1L
        const val NEGATIVE_RED = 2L
        const val NEGATIVE_TRANSPARENT = 3L

        const val KEY_TITLE = "key_title"
        const val KEY_BODY = "key_body"
        const val KEY_ICON = "key_icon"
        const val KEY_DATA = "key_data"
        const val KEY_BUTTON = "key_button"

        private const val KEY_TYPE = "key_type"


        @Retention(AnnotationRetention.SOURCE)
        @IntDef(POSITIVE, NEGATIVE_RED, NEGATIVE_TRANSPARENT)
        annotation class Type

        private fun newInstance(
                @Type type: Long,
                iconResId: Int,
                title: Int,
                body: Int,
                button: Int,
                data: Bundle?): DefaultDialogFragment {
            val fragment = DefaultDialogFragment()
            val args = Bundle()
            args.putInt(KEY_TITLE, title)
            args.putInt(KEY_BODY, body)
            args.putInt(KEY_ICON, iconResId)
            args.putLong(KEY_TYPE, type)
            args.putInt(KEY_BUTTON, button)
            args.putBundle(KEY_DATA, data)
            fragment.arguments = args
            return fragment
        }
    }

    private var defaultDialogClickListener: DefaultDialogClickListener? = null
    private var dialogDismissListener: DialogDismissListener? = null

    private var type: Long = -1
    private var button: Int = -1
    private var body: Int = -1
    private var title: Int = -1
    private var iconResId: Int = -1
    private var data: Bundle? = null

    override
    fun setupLayoutView(): Int = R.layout.fragment_dialog_default

    override
    fun onSetupInstance() {

    }

    override
    fun onSetupView() {
        when (type) {
            POSITIVE -> setupPositiveType()
            NEGATIVE_RED -> setupNegativeRedType()
            NEGATIVE_TRANSPARENT -> setupNegativeTransparentType()
        }
        setupContent()
        btnConfirm.setOnClickListener(onClickListener())
        icCancel.setOnClickListener(onClickListener())
    }


    private fun setupPositiveType() {
        icCancel.setImageResource(R.drawable.ic_cancel_black)
        icCancel.alpha = getFloatDimen(R.dimen.default_alpha_icon_inactive_light_theme)
        btnConfirm.setBackgroundResource(R.drawable.selector_dialog_button_positive)
    }

    private fun setupNegativeRedType() {
        icCancel.setImageResource(R.drawable.ic_cancel_red)
        btnConfirm.setBackgroundResource(R.drawable.selector_dialog_button_negative_red)
    }

    private fun setupNegativeTransparentType() {
        icCancel.setImageResource(R.drawable.ic_cancel_red)
        btnConfirm.setBackgroundResource(R.drawable.selector_dialog_button_negative_transparent)
    }

    private fun setupContent() {
        tvTitle.show(title != -1)
        if (title != -1) tvTitle.text = getString(title)

        tvBody.show(body != -1)
        if (body != -1) tvBody.text = getString(body)

        icon.show(iconResId != -1)
        if (iconResId != -1) icon.setBackgroundResource(iconResId)

        if (button != -1) btnConfirm.text = getString(button)
    }

    override
    fun onPrepareInstance() {

    }

    override
    fun onRestoreArgument(bundle: Bundle) {
        super.onRestoreArgument(bundle)
        arguments?.apply{
            type = getLong(KEY_TYPE)
            title = getInt(KEY_TITLE)
            body = getInt(KEY_BODY)
            iconResId = getInt(KEY_ICON)
            button = getInt(KEY_BUTTON)
            data = getBundle(KEY_DATA)
        }
    }

    override
    fun onRestoreView(savedInstanceState: Bundle) {
        super.onRestoreView(savedInstanceState)
    }

    override
    fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override
    fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        dialogDismissListener?.onDialogDismiss()
    }

    private fun onClickListener(): View.OnClickListener
            = View.OnClickListener { v ->
        when (v) {
            btnConfirm -> defaultDialogClickListener?.onClickNeutral(data) ?: dismiss()
            icCancel -> defaultDialogClickListener?.onClickCancel(data) ?: dismiss()
        }
    }


    class Builder {
        private var defaultDialogClickListener: DefaultDialogClickListener? = null
        private var dialogDismissListener: DialogDismissListener? = null

        private var type: Long = POSITIVE
        private var button: Int = -1
        private var body: Int = -1
        private var title: Int = -1
        private var iconResId = -1
        private var data: Bundle? = null

        fun setType(@Type type: Long): Builder {
            this.type = type
            return this
        }

        fun setButton(@StringRes button: Int): Builder {
            this.button = button
            return this
        }

        fun setTitle(@StringRes title: Int): Builder {
            this.title = title
            return this
        }

        fun setIcon(@DrawableRes resId: Int): Builder {
            this.iconResId = resId
            return this
        }

        fun setBody(@StringRes body: Int): Builder {
            this.body = body
            return this
        }

        fun setData(data: Bundle?): Builder {
            this.data = data
            return this
        }

        fun setDefalutClickListener(listener: DefaultDialogClickListener?): Builder {
            this.defaultDialogClickListener = listener
            return this
        }

        fun setDialogDismissListener(listener: DialogDismissListener?): Builder {
            this.dialogDismissListener = listener
            return this
        }


        fun build(): DefaultDialogFragment {
            return newInstance(
                    type = type,
                    title = title,
                    body = body,
                    button = button,
                    iconResId = iconResId,
                    data = data).apply {
                this.defaultDialogClickListener = defaultDialogClickListener
                this.dialogDismissListener = dialogDismissListener
            }
        }
    }
}
