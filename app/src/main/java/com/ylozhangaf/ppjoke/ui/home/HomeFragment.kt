package com.ylozhangaf.ppjoke.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.ylozhangaf.libnavannotation.FragmentDestination
import com.ylozhangaf.ppjoke.AbsListFragment
import com.ylozhangaf.ppjoke.R
import com.ylozhangaf.ppjoke.model.Feed

@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : AbsListFragment<Feed>() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun getAdapter(): PagedListAdapter<Feed, RecyclerView.ViewHolder> {
        TODO("")
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        TODO("Not yet implemented")
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        TODO("Not yet implemented")
    }
}