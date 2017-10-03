package com.nextzy.library.base.mvvm.layer1View


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.nextzy.library.base.mvvm.exception.ViewModelNotNullException
import com.nextzy.library.base.mvvm.exception.ViewModelNotSetupException
import com.nextzy.library.base.mvvm.layer2ViewModel.BaseViewModel
import com.nextzy.library.base.view.holder.base.BaseViewHolder
import java.lang.ref.WeakReference


/**
 * Created by「 The Khaeng 」on 20 Sep 2017 :)
 */

abstract class BaseMvvmListAdapter<VH : BaseViewHolder<*>, VM : BaseViewModel> : RecyclerView.Adapter<VH> {

    protected val context: Context
    protected var activity: WeakReference<FragmentActivity>? = null
    protected var fragment: WeakReference<Fragment>? = null
    protected var listener: OnClickHolderItemListener<VH>? = null

    var recyclerView: RecyclerView? = null
        private set

    val viewModelShared: VM?
        get() {
            if (setupViewModel() == null) throw ViewModelNotSetupException()
            if (fragment != null) {
                return ViewModelProviders.of(fragment?.get()?.activity!!)
                        .get(setupViewModel())
            } else if (activity != null) {
                return ViewModelProviders.of(activity?.get()!!)
                        .get(setupViewModel())
            }
            return null
        }

    val viewModel: VM
        get() {
            if (setupViewModel() == null) throw ViewModelNotSetupException()
            return if (fragment != null) {
                ViewModelProviders.of(fragment?.get()!!)
                        .get(setupViewModel())
            } else {
                ViewModelProviders.of(activity?.get()!!)
                        .get(setupViewModel())
            }
        }

    interface OnClickHolderItemListener<in VH> {
        fun onClickHolder(v: VH, position: Int)
    }

    constructor(activity: FragmentActivity) {
        this.context = activity
        this.activity = WeakReference(activity)
    }

    constructor(fragment: Fragment) {
        this.context = fragment.context
        this.fragment = WeakReference(fragment)
    }


    fun setOnClickHolderItemListener(listener: OnClickHolderItemListener<VH>) {
        this.listener = listener
    }


    override
    fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun getViewModel(viewModelClass: Class<VM>): VM? {
        if (setupViewModel() == null) throw ViewModelNotNullException()
        if (fragment != null) {
            return ViewModelProviders.of(fragment?.get()!!)
                    .get(viewModelClass)
        } else if (activity != null) {
            return ViewModelProviders.of(activity?.get()!!)
                    .get(viewModelClass)
        }
        return null
    }

    abstract fun setupViewModel(): Class<VM>?


    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH? {
        return null
    }

    override
    fun onBindViewHolder(holder: VH, position: Int) {
        if (listener != null) {
            holder.itemView.rootView.setOnClickListener(setOnClickItem(holder))
        }
    }

    private fun setOnClickItem(holder: VH): View.OnClickListener {
        return View.OnClickListener { v ->
            listener?.onClickHolder(
                    holder,
                    holder.adapterPosition )
        }
    }

}
