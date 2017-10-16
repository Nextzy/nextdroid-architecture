package com.nextzy.nextdroidapp.module.main

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.nextzy.library.base.mvvm.layer1View.BaseMvvmListAdapter
import com.nextzy.nextdroidapp.R
import com.nextzy.nextdroidapp.module.main.adapter.PictureListAdapter
import com.nextzy.tabcustomize.base.mvvm.CustomMvvmActivity
import com.thekhaeng.pushdownanim.PushDownAnim
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import kotlinx.android.synthetic.main.activity_main.rv_picture as rvPicture
import kotlinx.android.synthetic.main.activity_main.swipe_layout as swipeLayout

class MainActivity : CustomMvvmActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var pictureAdapter: PictureListAdapter

    companion object {
        private const val SPAN = 2
    }

    override
    fun setupViewModel() {
        viewModel = getViewModel(MainViewModel::class.java)
    }

    override
    fun setupLayoutView(): Int = R.layout.activity_main


    override
    fun setupInstance() {
        pictureAdapter = PictureListAdapter(this)
        pictureAdapter.setOnClickHolderItemListener(onClickHolderItemListener())
    }

    override
    fun setupView() {
        val space = resources.getDimension(R.dimen.default_padding_margin_large).toInt()
        rvPicture.layoutManager = StaggeredGridLayoutManager(SPAN, StaggeredGridLayoutManager.VERTICAL)
        val marginDecoration = LayoutMarginDecoration(SPAN, space)
        rvPicture.addItemDecoration(marginDecoration)
        swipeLayout.setOnRefreshListener(onRefreshListener())
    }

    override
    fun initialize() {

    }

    override
    fun onRestoreArgument(bundle: Bundle) {
        super.onRestoreArgument(bundle)
    }

    override
    fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun onClickHolderItemListener(): BaseMvvmListAdapter.OnClickHolderItemListener<RecyclerView.ViewHolder>
            = object : BaseMvvmListAdapter.OnClickHolderItemListener<RecyclerView.ViewHolder> {
        override
        fun onClickHolder(v: RecyclerView.ViewHolder, position: Int) {
            PushDownAnim.setOnTouchPushDownAnim(v.itemView)
        }

    }

    private fun onRefreshListener(): SwipeRefreshLayout.OnRefreshListener
            = SwipeRefreshLayout.OnRefreshListener {
    }


}
