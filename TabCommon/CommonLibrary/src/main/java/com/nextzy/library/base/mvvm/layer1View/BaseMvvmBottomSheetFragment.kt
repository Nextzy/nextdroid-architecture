package com.nextzy.library.base.mvvm.layer1View

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.DimenRes
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.util.TypedValue
import android.view.View
import com.nextzy.library.base.mvvm.exception.NotSetLayoutException
import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 26 Aug 2017 :)
 */

abstract class BaseMvvmBottomSheetFragment
    : BottomSheetDialogFragment() {
    private var requestCode = -1
    private var data: Bundle? = null
    private var isDestroy = false
    private var resultCode = Activity.RESULT_CANCELED
    private var listener: OnFragmentDialogListener? = null
    private var isDismissWithResult = false

    companion object {
        val KEY_REQUEST_CODE = "key_request_code"
    }


    fun <VM : ViewModel> getViewModel(viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(this).get(viewModelClass)

    fun <VM : ViewModel> getViewModel(key: String, viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(this).get(key, viewModelClass)

    fun <VM : ViewModel> getSharedViewModel(viewModelClass: Class<VM>): VM?
            = activity?.let { ViewModelProviders.of(it).get(viewModelClass) }


    override
    fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate: savedInstanceState=" + savedInstanceState)
        super.onCreate(savedInstanceState)
        isDestroy = false
        if (savedInstanceState == null) {
            val bundle = arguments
            if (bundle != null) {
                Timber.d("restoreArguments: arguments=" + bundle)
                onRestoreArgument(bundle)
            }
        } else {
            Timber.d("onRestoreInstanceState: savedInstanceState=" + savedInstanceState)
            onRestoreInstanceState(savedInstanceState)
        }
    }

    override
    fun onAttach(context: Context?) {
        super.onAttach(context)
        onSetupViewModel()
        try {
            this.listener = context as OnFragmentDialogListener?
        } catch (e: ClassCastException) {
            Timber.w(context.toString() + " isn't implement OnCompleteListener")
        }

    }

    @SuppressLint("RestrictedApi")
    override
    fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val layoutResId = setupLayoutView()
        if (setupLayoutView() == 0) throw NotSetLayoutException()
        val contentView = View.inflate(context, layoutResId, null)
        setContentView(contentView)

        val params = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior

        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override
                fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss()
                        behavior.state = STATE_COLLAPSED
                    }
                }

                override
                fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }

        dialog.setOnCancelListener { dialog1 ->
            setResultCode(Activity.RESULT_CANCELED)
            dismiss()
        }

        onBindView(contentView)
        onInitialize()
        onSetupView()
    }

    open fun setContentView(contentView: View) {
        dialog.setContentView(contentView)
    }

    override
    fun onResume() {
        super.onResume()
        Timber.d("onResume: ")
    }

    override
    fun onStart() {
        super.onStart()
        Timber.d("onStart: ")
    }

    override
    fun onStop() {
        super.onStop()
        Timber.d("onStop: ")
    }

    override
    fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        if (requestCode != -1 && !isDestroy && !isDismissWithResult) {
            withResult(resultCode, data)
        }
        if (requestCode != -1 && listener != null) {
            listener?.onFragmentDialogResult(requestCode, resultCode, data)
        }
    }

    /**
     * If Parent view is destroyed it will be calling onDestroy() before onFragmentDialogResult()
     */
    override
    fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: ")
        isDestroy = true
    }

    fun setResultData(data: Bundle?) {
        this.data = data
    }

    override
    fun onSaveInstanceState(outState: Bundle) {
        Timber.d("saveInstanceState: oustState=" + outState)
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_REQUEST_CODE, requestCode)
    }

    open fun onRestoreInstanceState(savedInstanceState: Bundle) {
        this.requestCode = savedInstanceState.getInt(KEY_REQUEST_CODE, -1)
    }

    fun getFloatDimen(@DimenRes resId: Int): Float {
        val outValue = TypedValue()
        resources.getValue(resId, outValue, true)
        return outValue.float
    }

    fun showForResult(target: Fragment, requestCode: Int) {
        this.requestCode = requestCode
        setTargetFragment(target, requestCode)
        show(target.fragmentManager, target.tag)
    }

    fun dismissWithResult(resultCode: Int, bundle: Bundle) {
        isDismissWithResult = true
        withResult(resultCode, bundle)
        dismiss()
    }

    private fun withResult(resultCode: Int, data: Bundle?) {
        val i = activity?.intent
        if (data != null) i?.putExtras(data)
        setResultCode(resultCode)
        setResultData(data)
        if (targetFragment != null) {
            targetFragment?.onActivityResult(targetRequestCode, resultCode, i)
        }
    }

    fun setResultCode(resultCode: Int) {
        this.resultCode = resultCode
    }

    abstract fun setupLayoutView(): Int

    open fun onSetupViewModel() {}

    abstract fun onBindView(view: View)

    open fun onInitialize() {}

    open fun onSetupView() {}

    open fun onRestoreArgument(bundle: Bundle) {}

    open fun onPrepareInstance() {}


}