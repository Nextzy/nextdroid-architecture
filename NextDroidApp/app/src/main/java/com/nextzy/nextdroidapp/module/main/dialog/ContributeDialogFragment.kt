package com.nextzy.nextdroidapp.module.main.dialog


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.nextzy.nextdroidapp.R
import com.nextzy.tabcustomize.base.mvvm.dialog.CustomMvvmDialogFragment
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.fragment_dialog_contribute.btn_confirm as btnConfirm
import kotlinx.android.synthetic.main.fragment_dialog_contribute.container_anonymous as containerAnonymous
import kotlinx.android.synthetic.main.fragment_dialog_contribute.container_user1 as containerUser1
import kotlinx.android.synthetic.main.fragment_dialog_contribute.ic_cancel as icCancel


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

class ContributeDialogFragment : CustomMvvmDialogFragment() {

    companion object {
        fun newInstance(): ContributeDialogFragment {
            val fragment = ContributeDialogFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override
    fun setupLayoutView(): Int = R.layout.fragment_dialog_contribute


    override
    fun setupView() {
        super.setupView()
        PushDownAnim.setOnTouchPushDownAnim(containerUser1)
                .setOnClickListener(onClickListener())
                .setScale(0.98f)

        PushDownAnim.setOnTouchPushDownAnim(containerAnonymous)
                .setOnClickListener(onClickListener())
                .setScale(0.98f)

        icCancel.setOnClickListener(onClickListener())
        btnConfirm.setOnClickListener(onClickListener())
    }


    /* =========================== Private method =============================================== */
    private fun openWebBrowser(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(intent)
        }
    }

    /* =========================== Callback method =============================================== */
    private fun onClickListener(): View.OnClickListener
            = View.OnClickListener { v ->
        when (v) {
            containerUser1 -> openWebBrowser("https://github.com/TheKhaeng")
            containerAnonymous -> openWebBrowser("https://github.com/Nextzy/nextdroid-architecture")
            icCancel -> dismiss()
            btnConfirm -> dismiss()
        }
    }



    class Builder {
        fun build(): ContributeDialogFragment {
            return newInstance()
        }
    }


}

