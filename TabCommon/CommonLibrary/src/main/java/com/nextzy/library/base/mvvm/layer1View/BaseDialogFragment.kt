package com.nextzy.library.base.mvvm.layer1View

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.DimenRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.nextzy.library.base.delegate.RxDelegation
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.lang.ClassCastException

/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */
abstract class BaseDialogFragment
    : DialogFragment() {
    private var requestCode = -1
    private var data: Bundle? = null
    private var isDestroy = false
    private var listener: OnFragmentDialogListener? = null
    private var resultCode = Activity.RESULT_CANCELED
    private var isDismissWithResult = false
    private val rxDelegation = RxDelegation()


    companion object {
        val KEY_REQUEST_CODE = "key_request_code"
    }


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
        try {
            this.listener = context as OnFragmentDialogListener?
        } catch (e: ClassCastException) {
            Timber.w(context.toString() + " isn't implement OnCompleteListener")
        }

    }

    override
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(true)
        isCancelable = true
        dialog.setOnCancelListener { dialog ->
            setResultCode(Activity.RESULT_CANCELED)
            dismiss()
        }
        return inflater?.inflate(setupLayoutView(), container, false)
    }

    override
    fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView(view)
        onSetupView()
        if (savedInstanceState == null) {
            onPrepareInstance()
        } else {
            onRestoreView(savedInstanceState)
        }
    }


    override
    fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        if (requestCode != -1 && !isDestroy && !isDismissWithResult) {
            withResult(resultCode, data)
        }
        if (requestCode != -1) {
            listener?.onFragmentDialogResult(requestCode, resultCode, data)
        }
    }

    /**
     * If Parent view is destroyed it will be calling onDestroy() before onFragmentDialogResult()
     */
    override
    fun onDestroy() {
        super.onDestroy()
        isDestroy = true
        rxDelegation.clearAllDisposables()
    }


    fun addDisposable(d: Disposable) {
        rxDelegation.addDisposable(d)
    }


    fun setResultData(data: Bundle?) {
        this.data = data
    }

    override
    fun onSaveInstanceState(outState: Bundle?) {
        Timber.d("saveInstanceState: oustState=" + outState)
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_REQUEST_CODE, requestCode)
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

    fun showForResult(activity: FragmentActivity, requestCode: Int) {
        this.requestCode = requestCode
        show(activity.supportFragmentManager, activity.javaClass.simpleName)
    }

    fun dismissWithResult(resultCode: Int, extrasBundle: Bundle) {
        isDismissWithResult = true
        withResult(resultCode, extrasBundle)
        dismiss()
    }

    private fun withResult(resultCode: Int, data: Bundle?) {
        val i = activity.intent
        if (data != null) i.putExtras(data)
        setResultCode(resultCode)
        setResultData(data)
        if (targetFragment != null) {
            targetFragment.onActivityResult(targetRequestCode, resultCode, i)
        }
    }

    open fun onRestoreInstanceState(savedInstanceState: Bundle) {
        this.requestCode = savedInstanceState.getInt(KEY_REQUEST_CODE, -1)
    }

    fun setResultCode(resultCode: Int) {
        this.resultCode = resultCode
    }

    abstract fun setupLayoutView(): Int

    open fun onBindView(view: View?){ }

    open fun onSetupInstance() {}

    open fun onSetupView() {}

    open fun onRestoreView(savedInstanceState: Bundle) {}

    open fun onRestoreArgument(bundle: Bundle) {}

    open fun onPrepareInstance() {}

}