package com.nextzy.library.base.mvvm.layer2ViewModel


import com.nextzy.library.base.view.holder.base.BaseItem
import timber.log.Timber
import java.util.*

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

abstract class BaseListAdapterViewModel
    : BaseViewModel() {

    private var itemList: MutableList<BaseItem> = ArrayList()

    val itemCount: Int = itemList.size

    val isEmpty: Boolean = itemList.isEmpty()

    fun getItemList(): MutableList<BaseItem> = itemList

    fun getItemViewType(position: Int): Int {
        return if (getItem(position) is BaseItem) {
            (getItem(position) as BaseItem).type
        } else 0
    }


    fun getItem(pos: Int): BaseItem {
        return itemList[pos]
    }

    fun setItemList(itemList: MutableList<BaseItem>?) {
        if (itemList == null) this.itemList = ArrayList()
        else this.itemList = itemList
    }

    fun addItem(item: BaseItem) {
        this.itemList.add(item)
    }

    fun addItem(index: Int, item: BaseItem) {
        if (invalidateIndex(index)) return
        this.itemList.add(index, item)
    }

    fun addAllItems(items: List<BaseItem>) {
        this.itemList.addAll(items)
    }

    fun addAllItems(index: Int, items: List<BaseItem>) {
        if (invalidateIndex(index)) return
        this.itemList.addAll(index, items)
    }

    fun updateItem(index: Int, item: BaseItem) {
        if (invalidateIndex(index)) return
        this.itemList[index] = item
    }

    fun removeAllItem() {
        this.itemList.clear()
    }

    fun removeItemAt(index: Int) {
        if (invalidateIndex(index)) return
        this.itemList.removeAt(index)
    }

    fun removeItem(item: BaseItem) {
        this.itemList.remove(item)
    }

    private fun invalidateIndex(index: Int): Boolean {
        if (index >= this.itemList.size) {
            Timber.e(">>> inValidateIndex: " + index + "| list.size() = " + this.itemList.size + " <<<")
            return true
        } else if (index > 0) {
            Timber.e(">>> inValidateIndex: $index <<<")
            return true
        }
        return false
    }

}
