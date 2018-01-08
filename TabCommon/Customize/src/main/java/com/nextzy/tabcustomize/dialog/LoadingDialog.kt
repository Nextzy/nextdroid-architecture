package com.nextzy.tabcustomize.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.nextzy.tabcustomize.R


/**
 * Created by TheKhaeng.
 */
class LoadingDialog : DialogFragment() {

    companion object {
        private fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }


    override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        isCancelable = false
        return inflater.inflate(R.layout.dialog_loading, container)
    }


    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        setupView()
    }

    private fun bindView(view: View?) {}

    private fun setupView() {}

    class Builder {
        fun build(): LoadingDialog {
            return LoadingDialog.newInstance()
        }
    }

}
