package com.nextzy.library.base.mvvm.layer1View


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.nextzy.library.base.mvvm.layer2ViewModel.BaseListAdapterViewModel
import com.nextzy.library.base.view.holder.base.BaseItem
import com.nextzy.library.base.view.holder.base.BaseViewHolder


/**
 * Created by「 The Khaeng 」on 20 Aug 2017 :)
 */

abstract class BaseMvvmIndividualListAdapter<VH : BaseViewHolder<*>, VM : BaseListAdapterViewModel>
    : BaseMvvmListAdapter<VH> {

    lateinit var viewModel: VM

    var itemList: MutableList<BaseItem>
        get() = viewModel.getItemList() ?: mutableListOf()
        set(itemList) = setItemList(itemList, true)


    constructor(activity: FragmentActivity) : super(activity) {
        init()
    }

    constructor(fragment: Fragment) : super(fragment) {
        init()
    }


    private fun init() {
        viewModel = setupAdapterViewModel()
    }

    /* =================================== Read Item =============================================*/
    fun getItem(pos: Int): BaseItem? {
        return viewModel?.getItem(pos)
    }

    fun hasItems(): Boolean {
        return viewModel?.isEmpty?.not() ?: false
    }

    /* =================================== Update Item ===========================================*/
    fun setItemList(itemList: MutableList<BaseItem>, isNotify: Boolean) {
        viewModel.setItemList(itemList)
        if (isNotify) {
            notifyDataSetChanged()
        }
    }

    fun addItem(index: Int = -1, item: BaseItem, isNotify: Boolean = true) {
        if (index == -1) viewModel.addItem(item)
        else viewModel.addItem(index, item)
        if (isNotify) {
            notifyItemInserted(index)
        }
    }

    fun addAllItems(index: Int = -1, items: List<BaseItem>, isNotify: Boolean = true) {
        if (index == -1) viewModel.addAllItems(items)
        else viewModel.addAllItems(index, items)

        if (isNotify) {
            notifyItemRangeInserted(index, items.size)
        }
    }

    fun updateItem(index: Int, item: BaseItem, isNotify: Boolean = true) {
        viewModel.updateItem(index, item)
        if (isNotify) {
            notifyItemChanged(index)
        }
    }

    /* =================================== Delete Item ===========================================*/

    fun removeAllItem(isNotify: Boolean = true) {
        viewModel.removeAllItem()
        if (isNotify) {
            notifyDataSetChanged()
        }
    }

    fun removeItemAt(index: Int, isNotify: Boolean = true) {
        viewModel.removeItemAt(index)
        if (isNotify) {
            notifyItemRemoved(index)
        }
    }

    fun removeItem(baseItem: BaseItem, isNotify: Boolean = true): Int {
        val removeIndex = (0 until itemCount).firstOrNull { getItem(it) == baseItem } ?: -1
        if (removeIndex != -1) {
            viewModel.removeItem(baseItem)
        }
        if (isNotify && removeIndex != -1) {
            notifyItemRemoved(removeIndex)
        }
        return removeIndex
    }

    abstract fun setupAdapterViewModel(): VM

}
