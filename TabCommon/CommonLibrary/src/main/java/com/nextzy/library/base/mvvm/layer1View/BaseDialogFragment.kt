package com.nextzy.library.base.mvvm.layer1View

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
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
import com.nextzy.library.base.delegate.DefaultSnackbarDelegate
import com.nextzy.library.base.delegate.DefaultSnackbarInterface
import com.nextzy.library.base.delegate.RxDelegation
import com.nextzy.setting.view.util.SettingPreferenceDelegate
import com.nextzy.setting.view.util.SettingPreferenceInterface
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.lang.ClassCastException

/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */
abstract class BaseDialogFragment
    : DialogFragment(),
      DefaultSnackbarInterface,
      SettingPreferenceInterface {
    private var requestCode = -1
    private var data: Bundle? = null
    private var isDestroy = false
    private var listener: OnFragmentDialogListener? = null
    private var resultCode = Activity.RESULT_CANCELED
    private var isDismissWithResult = false
    private val rxDelegation = RxDelegation()

    private lateinit var snackbarDelegate: DefaultSnackbarDelegate
    private lateinit var settingDelegate: SettingPreferenceDelegate

    companion object {
        val KEY_REQUEST_CODE = "key_request_code"
    }


    override
    fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate: savedInstanceState=" + savedInstanceState)
        super.onCreate(savedInstanceState)
        isDestroy = false
        settingDelegate = SettingPreferenceDelegate(context)
        snackbarDelegate = DefaultSnackbarDelegate(activity)
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
        snackbarDelegate.setSnackbarTargetView(view)
        bindView(view)
        setupView()
        if (savedInstanceState == null) {
            initialize()
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

    override
    fun setSnackbarTargetView(target: View?) {
        snackbarDelegate.setSnackbarTargetView(target)
    }

    override
    fun showSnackbarCustom(colorId: Int, iconId: Int, message: String, duration: Int) {
        snackbarDelegate.showSnackbarCustom(colorId, iconId, message, duration)
    }

    override
    fun showSnackbarCustomDismiss(colorId: Int, iconId: Int, message: String) {
        snackbarDelegate.showSnackbarCustomDismiss(colorId, iconId, message)
    }

    override
    fun showSnackbarSuccess(message: String, duration: Int) {
        snackbarDelegate.showSnackbarSuccess(message, duration)
    }

    override
    fun showSnackbarWarning(message: String, duration: Int) {
        snackbarDelegate.showSnackbarWarning(message, duration)
    }

    override
    fun showSnackbarError(message: String, duration: Int) {
        snackbarDelegate.showSnackbarError(message, duration)
    }

    override
    fun showSnackbarInfo(message: String, duration: Int) {
        snackbarDelegate.showSnackbarInfo(message, duration)
    }

    override
    fun showSnackbarSuccessDismiss(message: String) {
        snackbarDelegate.showSnackbarSuccessDismiss(message)
    }

    override
    fun showSnackbarWarningDismiss(message: String) {
        snackbarDelegate.showSnackbarWarningDismiss(message)
    }

    override
    fun showSnackbarErrorDismiss(message: String) {
        snackbarDelegate.showSnackbarErrorDismiss(message)
    }

    override
    fun showSnackbarInfoDismiss(message: String) {
        snackbarDelegate.showSnackbarInfoDismiss(message)
    }

    fun setResultCode(resultCode: Int) {
        this.resultCode = resultCode
    }

    abstract fun setupLayoutView(): Int

    open fun bindView(view: View?){ }

    open fun setupInstance() {}

    open fun setupView() {}

    open fun onRestoreView(savedInstanceState: Bundle) {}

    open fun onRestoreArgument(bundle: Bundle) {}

    open fun initialize() {}

    /* ============================== Persist =================================================== */
    override
    fun persistString(key: String, value: String): Boolean {
        return settingDelegate.persistString(key, value)
    }

    override
    fun getPersistedString(key: String, defaultValue: String): String {
        return settingDelegate.getPersistedString(key, defaultValue)
    }

    override
    fun persistStringSet(key: String, values: Set<String>): Boolean {
        return settingDelegate.persistStringSet(key, values)
    }

    override
    fun getPersistedStringSet(key: String, defaultValue: Set<String>): Set<String> {
        return settingDelegate.getPersistedStringSet(key, defaultValue)
    }

    override
    fun persistInt(key: String, value: Int): Boolean {
        return settingDelegate.persistInt(key, value)
    }

    override
    fun getPersistedInt(key: String, defaultValue: Int): Int {
        return settingDelegate.getPersistedInt(key, defaultValue)
    }

    override
    fun persistFloat(key: String, value: Float): Boolean {
        return settingDelegate.persistFloat(key, value)
    }

    override
    fun getPersistedFloat(key: String, defaultReturnValue: Float): Float {
        return settingDelegate.getPersistedFloat(key, defaultReturnValue)
    }

    override
    fun persistLong(key: String, value: Long): Boolean {
        return settingDelegate.persistLong(key, value)
    }

    override
    fun getPersistedLong(key: String, defaultValue: Long): Long {
        return settingDelegate.getPersistedLong(key, defaultValue)
    }

    override
    fun persistedBoolean(key: String, value: Boolean): Boolean {
        return settingDelegate.persistedBoolean(key, value)
    }

    override
    fun getPersistedBoolean(key: String, defaultValue: Boolean): Boolean {
        return settingDelegate.getPersistedBoolean(key, defaultValue)
    }

    override
    fun getSharedPreferences(): SharedPreferences {
        return settingDelegate.sharedPreferences
    }
}