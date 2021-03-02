package com.ylozhangaf.ppjoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.ylozhangaf.libcommon.view.EmptyView
import com.ylozhangaf.ppjoke.databinding.LayoutRefreshViewBinding

abstract class AbsListFragment<T> : Fragment(), OnRefreshListener, OnLoadMoreListener {

    private lateinit var binding : LayoutRefreshViewBinding
    private lateinit var recycleView : RecyclerView
    private lateinit var refreshLayout : SmartRefreshLayout
    private lateinit var emptyView : EmptyView
    private lateinit var adapter: PagedListAdapter<T, RecyclerView.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutRefreshViewBinding.inflate(inflater, container, false)
        recycleView = binding.recyclerView
        refreshLayout = binding.refreshLayout
        emptyView = binding.emptyView

        initRefreshView()
        initRecyclerView()
        return binding.root
    }

    private fun initRefreshView() {
        refreshLayout.setEnableRefresh(true)
        refreshLayout.setEnableLoadMore(true)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)
    }

    private fun initRecyclerView() {
        adapter = getAdapter()
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recycleView.itemAnimator = null
    }

    fun submitList(pagedList: PagedList<T>) {
        val isNotEmpty = pagedList.isNotEmpty()
        if (isNotEmpty) {
            adapter.submitList(pagedList)
        }
        finishRefresh(isNotEmpty)
    }

    fun finishRefresh(hasData: Boolean) {
        val cacheList = adapter.currentList
        val cacheListHasData = hasData || !cacheList.isNullOrEmpty()
        val state = refreshLayout.state
        if (state.isFooter && state.isOpening) {
            refreshLayout.finishLoadMore()
        } else if (state.isHeader && state.isOpening) {
            refreshLayout.finishRefresh()
        }

        if (hasData) {
            emptyView.visibility = View.GONE
        } else {
            emptyView.visibility = View.VISIBLE
        }
    }

    abstract fun getAdapter() : PagedListAdapter<T, RecyclerView.ViewHolder>

}