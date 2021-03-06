package com.e.commerce.ui.fragments.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.e.commerce.data.model.home.BannerPojo
import com.e.commerce.databinding.ItemBannerBinding
import com.smarteist.autoimageslider.SliderViewAdapter

// Created by Hussein_Mohammad on 5/7/2021.

@SuppressLint("NotifyDataSetChanged")
class BannerSliderAdapter(var context: Context, var bannerImagesList: ArrayList<BannerPojo>) :
    SliderViewAdapter<BannerSliderAdapter.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        context = parent.context
        return SliderAdapterVH(
            ItemBannerBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) = viewHolder.bind(bannerImagesList[position])

    override fun getCount(): Int = if (bannerImagesList.isNotEmpty()) bannerImagesList.size else 0

    inner class SliderAdapterVH(var binding: ItemBannerBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {

        fun bind(pojo: BannerPojo) {
            Glide.with(itemView)
                .load(pojo.image)
                .into(binding.imgBanner)
        }
    }
}
