package com.enhanceit.demo.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.enhanceit.demo.R
import com.enhanceit.demo.utils.Status
import com.enhanceit.demo.databinding.ActivityLaunchesBinding
import com.enhanceit.demo.utils.gone
import com.enhanceit.demo.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_launches.*

@AndroidEntryPoint
class LaunchesActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private val launchesViewModel: LaunchesViewModel by viewModels()
    private lateinit var binding: ActivityLaunchesBinding
    private lateinit var adapter: LaunchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launches)
        supportActionBar?.title = getString(R.string.title)
        initRecyclerView()
        fetchData()
        ObserveData()
    }

    private fun fetchData() {
        launchesViewModel.fetchLaunches()
    }

    private fun initRecyclerView() {
        binding.rvLaunches.layoutManager = LinearLayoutManager(this)
        adapter = LaunchAdapter()
        binding.rvLaunches.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener(this)
    }

    private fun ObserveData() {
        launchesViewModel.launches.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    layout_progressbar.gone()
                    it.let {
                        adapter.setList(it.data!!)
                        adapter.notifyDataSetChanged()
                    }
                }
                Status.LOADING -> {
                    layout_progressbar.visible()
                    progress_circular.visible()
                    tvError.gone()
                }
                Status.ERROR -> {
                    layout_progressbar.visible()
                    tvError.visible()
                    tvError.text = it.message
                    progress_circular.gone()
                }
            }
        })
    }

    override fun onRefresh() {
        adapter.clear()
        fetchData()
        binding.swipeRefresh.isRefreshing = false
    }
}