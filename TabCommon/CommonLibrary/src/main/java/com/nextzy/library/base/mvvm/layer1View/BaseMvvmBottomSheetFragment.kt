package com.nextzy.library.base.mvvm.layer1View

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.support.annotation.DimenRes
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.util.TypedValue
import android.view.View
import com.nextzy.library.base.delegate.DefaultSnackbarDelegate
import com.nextzy.library.base.delegate.DefaultSnackbarInterface
import com.nextzy.library.base.mvvm.exception.NotSetLayoutException
import com.nextzy.library.base.mvvm.exception.ViewModelNotNullException
import com.nextzy.library.base.mvvm.layer2ViewModel.BaseDialogViewModel
import com.nextzy.setting.view.util.SettingPreferenceDelegate
import com.nextzy.setting.view.util.SettingPreferenceInterface
import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 26 Aug 2017 :)
 */

abstract class BaseMvvmBottomSheetFragment<VM : BaseDialogViewModel>
    : BottomSheetDialogFragment(),
      DefaultSnackbarInterface,
      SettingPreferenceInterface {
    private var requestCode = -1
    private var data: Bundle? = null
    private var isDestroy = false
    private var resultCode = Activity.RESULT_CANCELED
    private var listener: OnFragmentDialogListener? = null
    private var isDismissWithResult = false

    private lateinit var snackbarDelegate: DefaultSnackbarDelegate
    private lateinit var settingDelegate: SettingPreferenceDelegate

    companion object {
        val KEY_REQUEST_CODE = "key_request_code"
    }




    fun <VM : ViewModel> getViewModel(viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(this).get(viewModelClass)


    fun <VM : ViewModel> getSharedViewModel(viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(activity).get(viewModelClass)


    override
    fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate: savedInstanceState=" + savedInstanceState)
        super.onCreate(savedInstanceState)
        isDestroy = false
        settingDelegate = SettingPreferenceDelegate(context)
        snackbarDelegate = DefaultSnackbarDelegate(this)
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
        setupViewModel()
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
        snackbarDelegate.setSnackbarTargetView(contentView)
        dialog.setContentView(contentView)


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

        bindView(contentView)
        setupInstance()
        setupView()
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
    fun onSaveInstanceState(outState: Bundle?) {
        Timber.d("saveInstanceState: oustState=" + outState)
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_REQUEST_CODE, requestCode)
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
        val i = activity.intent
        if (data != null) i.putExtras(data)
        setResultCode(resultCode)
        setResultData(data)
        if (targetFragment != null) {
            targetFragment.onActivityResult(targetRequestCode, resultCode, i)
        }
    }

    fun setResultCode(resultCode: Int) {
        this.resultCode = resultCode
    }

    fun getViewModel(viewModelClass: Class<VM>): VM {
        if (setupViewModel() == null) throw ViewModelNotNullException()
        return ViewModelProviders.of(this)
                .get(viewModelClass)
    }

    abstract fun setupLayoutView(): Int

    open fun setupViewModel(){ }

    abstract fun bindView(view: View)

    open fun setupInstance() {}

    open fun setupView() {}

    open fun onRestoreArgument(bundle: Bundle) {}

    open fun initialize() {}

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