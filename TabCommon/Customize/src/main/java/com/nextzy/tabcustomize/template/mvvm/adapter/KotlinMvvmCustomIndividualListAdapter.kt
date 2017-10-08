package com.nextzy.tabcustomize.template.mvvm.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.ViewGroup
import com.nextzy.library.base.mvvm.exception.TypeNotMatchInAdapterException
import com.nextzy.library.base.view.holder.base.BaseViewHolder
import com.nextzy.tabcustomize.decorator.adapter.CustomMvvmIndividualListAdapter
import com.nextzy.tabcustomize.template.mvvm.adapter.holder.KotlinMvvmHolder
import com.nextzy.tabcustomize.template.mvvm.adapter.item.KotlinMvvmCustomItem
import com.nextzy.tabcustomize.template.mvvm.adapter.operator.KotlinMvvmCreator

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class KotlinMvvmCustomIndividualListAdapter
    : CustomMvvmIndividualListAdapter<BaseViewHolder<*>, KotlinMvvmCustomIndividualAdapterViewModel> {
    companion object {
        private val TAG = KotlinMvvmCustomIndividualListAdapter::class.java.simpleName
    }


    constructor(activity: FragmentActivity) : super(activity) {}

    constructor(fragment: Fragment) : super(fragment) {}

    override
    fun setupViewModel(): Class<KotlinMvvmCustomIndividualAdapterViewModel>
            = KotlinMvvmCustomIndividualAdapterViewModel::class.java

    override
    fun setSharedViewModel(): Boolean = false

    override
    fun getItemCount(): Int = viewModel.itemCount
//            viewModelShared.itemCount

    override
    fun getItemViewType(position: Int): Int = viewModel.getItemViewType(position)
//            viewModelShared.getItemViewType(position)


    override
    fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        super.onCreateViewHolder(viewGroup, viewType)
        if (viewType == KotlinMvvmCreator.TYPE_CUSTOM_ITEM) {
            return KotlinMvvmHolder(viewGroup)
        }
        throw TypeNotMatchInAdapterException(TAG)
    }

    override
    fun onBindViewHolder(vh: BaseViewHolder<*>, pos: Int) {
        super.onBindViewHolder(vh, pos)
        val i = itemList[pos]
        if (getItemViewType(pos) == KotlinMvvmCreator.TYPE_CUSTOM_ITEM) {
            val viewHolder = vh as KotlinMvvmHolder
            val item = i as KotlinMvvmCustomItem
            viewHolder.onBind(item)
        }
    }

}