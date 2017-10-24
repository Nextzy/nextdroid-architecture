package com.nextzy.nextdroidapp.module.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.nextzy.library.base.mvvm.layer1View.BaseMvvmListAdapter
import com.nextzy.library.decorator.EndlessScrollListener
import com.nextzy.library.extension.isTablet
import com.nextzy.library.extension.toast
import com.nextzy.library.extension.view.delay
import com.nextzy.nextdroidapp.R
import com.nextzy.nextdroidapp.module.main.adapter.PhotoListAdapter
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoItem
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoListItem
import com.nextzy.nextdroidapp.module.main.dialog.ContributeDialogFragment
import com.nextzy.nextdroidapp.module.photo.PhotoActivity
import com.nextzy.nextwork.engine.model.NetworkStatus
import com.nextzy.tabcustomize.base.extension.showDialog
import com.nextzy.tabcustomize.base.mvvm.CustomMvvmActivity
import com.nextzy.tabcustomize.base.repository.network.DefaultResource
import com.thekhaeng.pushdownanim.PushDownAnim
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.ic_info as icInfo
import kotlinx.android.synthetic.main.activity_main.ic_reload as icReload
import kotlinx.android.synthetic.main.activity_main.rv_picture as rvPhoto
import kotlinx.android.synthetic.main.activity_main.swipe_layout as swipeLayout


class MainActivity : CustomMvvmActivity() {

    companion object {
        const val REQUEST_OPEN_PHOTO = 100
        private const val SPAN_TWO = 2
        private const val SPAN_THREE = 3
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var photoAdapter: PhotoListAdapter
    private lateinit var manager: StaggeredGridLayoutManager

    override
    fun onSetupViewModel() {
        viewModel = getViewModel(MainViewModel::class.java)
        viewModel.photoListAfterIdLiveData.observe(this, observerPictureListAfterList())
        viewModel.photoListLiveData.observe(this, observerPictureList())
        viewModel.photoListBeforeIdLiveData.observe(this, observerPictureListBeforeList())
    }

    override
    fun setupLayoutView(): Int = R.layout.activity_main


    override
    fun onInitialize() {
        super.onInitialize()
        photoAdapter = PhotoListAdapter(this).apply {
            setHasStableIds(true)
            setOnClickHolderItemListener(onClickHolderItemListener())
        }
    }

    override
    fun onSetupView() {
        super.onSetupView()
        setSupportActionBar(toolbar)

        PushDownAnim
                .setOnTouchPushDownAnim(fab)
                .setOnClickListener(onClickListener())
                .setScale(0.95f)
        icReload.setOnClickListener(onClickListener())
        icInfo.setOnClickListener(onClickListener())
        manager = StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        }
        swipeLayout.setOnRefreshListener(onRefreshListener())


        rvPhoto.apply {
            setHasFixedSize(true)
            adapter = photoAdapter
            layoutManager = manager
            addOnScrollListener(EndlessScrollListener.create(onLoadMore()))
        }
    }


    override
    fun onPrepareInstance() {
        super.onPrepareInstance()
        viewModel
                .getMaxPhotoId()
                .subscribe({ id: Int ->
                               if (id == -1) {
                                   refreshData()
                               } else {
                                   viewModel.requestPhotoListAfterId(id)
                               }
                           })
    }

    override
    fun onRestoreView(savedInstanceState: Bundle) {
        super.onRestoreView(savedInstanceState)
        if (viewModel.photoListItemAll.isEmpty()) {
            onPrepareInstance()
        } else {
            photoAdapter.notifyPhotoDataSetChanged()
            manager.onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE)
        }
    }

    override
    fun onDestroy() {
        super.onDestroy()
        viewModel.saveData()
    }

    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_OPEN_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                val holderId = data?.getLongExtra(PhotoActivity.KEY_HOLDER_ID, -1L) ?: -1L
                val position = data?.getIntExtra(PhotoActivity.KEY_ITEM_POSITION, -1) ?: -1
                if (position != -1
                        && holderId != -1L
                        && photoAdapter.itemCount > 0                           // grid populated
                        && rvPhoto.findViewHolderForItemId(holderId) == null) {    // view not attached
                    // do something for restore
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /* =========================== Private method =============================================== */
    private fun showRefreshing() {
        swipeLayout.isRefreshing = true
    }

    private fun hideRefreshing() {
        delay(Action { swipeLayout.isRefreshing = false }, 1500)
    }

    private fun refreshData() {
        if (viewModel.photoListItemAll.isEmpty()) {
            loadData()
        } else {
            loadNewData()
        }
    }

    private fun loadData() {
        viewModel.requestPhotoList()
    }

    private fun loadMoreData() {
        val minId = viewModel.getMinimumPhotoId()
        viewModel.requestPhotoListBeforeId(minId)
    }

    private fun loadNewData() {
        val maxId = viewModel.getMaximumPhotoId()
        if (maxId <= 0) return
        viewModel.requestPhotoListAfterId(maxId)
    }


    private fun openPhotoActivity(id: Long, position: Int, photoItem: PhotoItem) {
        val data = Bundle()
        data.putParcelable(PhotoActivity.KEY_PHOTO_ITEM, photoItem)
        data.putInt(PhotoActivity.KEY_ITEM_POSITION, position)
        data.putLong(PhotoActivity.KEY_HOLDER_ID, id)
        openActivity(PhotoActivity::class.java, REQUEST_OPEN_PHOTO, data = data)
    }

    /* =========================== Callback method ============================================== */
    private fun onClickListener(): View.OnClickListener
            = View.OnClickListener { v ->
        when (v) {
            fab -> rvPhoto.smoothScrollToPosition(0)
            icReload -> {
                showRefreshing()
                viewModel.requestPhotoList(true)
                photoAdapter.notifyPhotoDataSetChanged()
            }
            icInfo -> showDialog(ContributeDialogFragment.Builder().build())
        }
    }

    private fun onClickHolderItemListener(): BaseMvvmListAdapter.OnClickHolderItemListener<RecyclerView.ViewHolder>
            = object : BaseMvvmListAdapter.OnClickHolderItemListener<RecyclerView.ViewHolder> {
        override
        fun onClickHolder(v: RecyclerView.ViewHolder, position: Int) {
            viewModel.getPhotoItem(position)?.let {
                openPhotoActivity(
                        photoAdapter.getItemId(position),
                        position,
                        it)
            }
        }

    }

    private fun onRefreshListener(): SwipeRefreshLayout.OnRefreshListener
            = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }


    private fun onLoadMore(): EndlessScrollListener.RefreshList
            = object : EndlessScrollListener.RefreshList {
        override
        fun onRefresh(pageNumber: Int): Boolean {
            toast(getString(R.string.load_more))
            loadMoreData()
            return true
        }
    }

    private fun getSpan(): Int {
        return when {
            isTablet -> SPAN_THREE
            resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> SPAN_THREE
            resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT -> SPAN_TWO
            else -> SPAN_TWO
        }
    }


    private fun observerPictureList(): Observer<DefaultResource<PhotoListItem>>
            = Observer { resource: DefaultResource<PhotoListItem>? ->
        when {
            resource?.status == NetworkStatus.SUCCESS -> {
                resource.data?.let {
                    viewModel.addPhotoItemList(data = resource.data)
                    photoAdapter.notifyPhotoDataSetChanged()
                }
                hideRefreshing()
            }
            resource?.status == NetworkStatus.ERROR -> {
                resource.message?.let { showSnackbarError(it) }
                hideRefreshing()
            }
            resource?.status == NetworkStatus.LOADING -> {
                // do nothing
            }
        }
    }

    private fun observerPictureListBeforeList(): Observer<DefaultResource<PhotoListItem>>
            = Observer { resource: DefaultResource<PhotoListItem>? ->
        when {
            resource?.status == NetworkStatus.SUCCESS -> {
                if (resource.data != null) {
                    viewModel.addPhotoItemList(data = resource.data)
                    photoAdapter.notifyPhotoDataSetChanged()
                    manager.onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE)
                }
                hideRefreshing()
            }
            resource?.status == NetworkStatus.ERROR -> {
                hideRefreshing()
                resource.message?.let { showSnackbarError(it) }
            }
            resource?.status == NetworkStatus.LOADING -> {
                // do nothing
            }
        }
    }


    private fun observerPictureListAfterList(): Observer<DefaultResource<PhotoListItem>>
            = Observer { resource: DefaultResource<PhotoListItem>? ->
        when {
            resource?.status == NetworkStatus.SUCCESS -> {
                if (resource.data != null) {
                    viewModel.addPhotoItemList(0, data = resource.data)
                    photoAdapter.notifyPhotoDataSetChanged()
                    manager.onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE)
                }
                hideRefreshing()
            }
            resource?.status == NetworkStatus.ERROR -> {
                hideRefreshing()
                resource.message?.let { showSnackbarError(it) }
            }
            resource?.status == NetworkStatus.LOADING -> {
                // do nothing
            }
        }
    }

}
