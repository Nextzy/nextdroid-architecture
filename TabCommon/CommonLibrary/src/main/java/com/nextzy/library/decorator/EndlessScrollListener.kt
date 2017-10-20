package com.nextzy.library.decorator

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager


/**
 * Created by「 The Khaeng 」on 18 Oct 2017 :)
 */
class EndlessScrollListener
private constructor(private val refreshList: RefreshList)
    : RecyclerView.OnScrollListener() {
    private var isLoading: Boolean = false
    private var hasMorePages: Boolean = false
    private var pageNumber = 0
    private var isRefreshing: Boolean = false
    private var pastVisibleItems: Int = 0

    interface RefreshList {
        fun onRefresh(pageNumber: Int): Boolean
    }

    companion object {
        fun create(refreshList: RefreshList): EndlessScrollListener = EndlessScrollListener(refreshList)
    }

    init {
        this.isLoading = false
        this.hasMorePages = true
    }


    override
    fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        recyclerView?.let {
            val manager = recyclerView.layoutManager as StaggeredGridLayoutManager

            val visibleItemCount = manager.childCount
            val totalItemCount = manager.itemCount
            val firstVisibleItems = manager.findFirstVisibleItemPositions(null)
            if (firstVisibleItems != null && firstVisibleItems.isNotEmpty()) {
                pastVisibleItems = firstVisibleItems[0]
            }

            if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
                isLoading = true
                if (hasMorePages && !isRefreshing) {
                    isRefreshing = true
                    Handler().postDelayed({
                                              if (refreshList.onRefresh(pageNumber)) {
                                                  notifyMorePages()
                                              } else {
                                                  noMorePages()
                                              }
                                          }, 200)
                }
            } else {
                isLoading = false
            }
        }
    }

    fun noMorePages() {
        this.hasMorePages = false
    }

    fun notifyMorePages() {
        isRefreshing = false
        pageNumber += 1
    }

}