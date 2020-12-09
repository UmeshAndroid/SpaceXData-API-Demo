package com.enhanceit.demo.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enhanceit.demo.R
import com.enhanceit.demo.data.model.Launches
import com.enhanceit.demo.databinding.LaunchItemBinding
import com.enhanceit.demo.utils.getFormatteDate
import com.enhanceit.demo.utils.getSuccessImage
import com.enhanceit.demo.utils.getUrl
import kotlin.collections.ArrayList

class LaunchAdapter() : RecyclerView.Adapter<LaunchViewHolder>() {
    private val launchList = ArrayList<Launches>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: LaunchItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.launch_item,
            parent,
            false
        )
        return LaunchViewHolder(binding)
    }

    override fun getItemCount() = this.launchList.size

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bind(this.launchList[position])
    }

    fun setList(launches: List<Launches>) {
        this.launchList.clear()
        this.launchList.addAll(launches)
    }

    fun clear() {
        this.launchList.clear()
        notifyDataSetChanged()
    }
}

class LaunchViewHolder(val binding: LaunchItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(launch: Launches) {
        binding.tvLaunchName.text = launch.name
        binding.tvLaunchDate.text = launch.static_fire_date_unix.getFormatteDate()

        Glide.with(binding.ivSuccess.context)
            .load(launch.success?.getSuccessImage(binding.ivLaunch.context))
            .into(binding.ivSuccess)

        Glide.with(binding.ivLaunch.context)
            .load(launch.links.patch.small.getUrl())
            .apply(
                RequestOptions().placeholder(R.drawable.image_not_available)
                    .error(R.drawable.image_not_available)
            )
            .into(binding.ivLaunch)
    }
}
