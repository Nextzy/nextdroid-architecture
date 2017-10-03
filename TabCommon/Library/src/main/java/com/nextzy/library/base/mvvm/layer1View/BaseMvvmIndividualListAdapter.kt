package com.nextzy.library.base.mvvm.layer1View


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.nextzy.library.base.mvvm.layer2ViewModel.BaseListAdapterViewModel
import com.nextzy.library.base.view.holder.base.BaseViewHolder


/**
 * Created by「 The Khaeng 」on 20 Aug 2017 :)
 */

abstract class BaseMvvmIndividualListAdapter<VH : BaseViewHolder<Any>, VM : BaseListAdapterViewModel>
    : BaseMvvmListAdapter<VH, VM> {


    var itemList: MutableList<Any>
        get() = privateViewModel?.getItemList() ?: mutableListOf()
        set(itemList) = setItemList(itemList, true)

    private val privateViewModel: VM?
        get() = if (setSharedViewModel()) {
            viewModelShared
        } else {
            viewModel
        }

    constructor(activity: FragmentActivity) : super(activity) {}

    constructor(fragment: Fragment) : super(fragment) {}


    /* =================================== Read Item =============================================*/
    fun getItem(pos: Int): Any? {
        return privateViewModel?.getItem(pos)
    }

    fun hasItems(): Boolean {
        return privateViewModel?.isEmpty?.not() ?: false
    }

    /* =================================== Update Item ===========================================*/
    fun setItemList(itemList: MutableList<Any>, isNotify: Boolean) {
        privateViewModel?.setItemList(itemList)
        if (isNotify) {
            notifyDataSetChanged()
        }
    }

    fun addItem(index: Int = -1, item: Any, isNotify: Boolean = true) {
        if (index == -1) privateViewModel?.addItem(item)
        else privateViewModel?.addItem(index, item)
        if (isNotify) {
            notifyItemInserted(index)
        }
    }

    fun addAllItems(index: Int = -1, items: List<Any>, isNotify: Boolean = true) {
        if (index == -1) privateViewModel?.addAllItems(items)
        else privateViewModel?.addAllItems(index, items)

        if (isNotify) {
            notifyItemRangeInserted(index, items.size)
        }
    }

    fun updateItem(index: Int, item: Any, isNotify: Boolean = true) {
        privateViewModel?.updateItem(index, item)
        if (isNotify) {
            notifyItemChanged(index)
        }
    }

    /* =================================== Delete Item ===========================================*/

    fun removeAllItem(isNotify: Boolean = true) {
        privateViewModel?.removeAllItem()
        if (isNotify) {
            notifyDataSetChanged()
        }
    }

    fun removeItemAt(index: Int, isNotify: Boolean = true) {
        privateViewModel?.removeItemAt(index)
        if (isNotify) {
            notifyItemRemoved(index)
        }
    }

    fun removeItem(baseItem: Any, isNotify: Boolean = true): Int {
        val removeIndex = (0 until itemCount).firstOrNull { getItem(it) == baseItem } ?: -1
        if (removeIndex != -1) {
            privateViewModel?.removeItem(baseItem)
        }
        if (isNotify && removeIndex != -1) {
            notifyItemRemoved(removeIndex)
        }
        return removeIndex
    }

    override
    fun onBindViewHolder(holder: VH, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.item = getItem(holder.adapterPosition)
    }

    abstract fun setSharedViewModel(): Boolean

}
