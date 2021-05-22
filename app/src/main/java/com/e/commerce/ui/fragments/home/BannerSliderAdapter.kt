package com.e.commerce.ui.fragments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.e.commerce.data.model.home.BannerPojo
import com.e.commerce.databinding.ItemBannerBinding
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

// Created by Hussein_Mohammad on 5/7/2021.

class BannerSliderAdapter(var context: Context) :
    SliderViewAdapter<BannerSliderAdapter.SliderAdapterVH>() {

    private lateinit var bannerImagesList: MutableList<BannerPojo>

    fun renewItem(bannerItems: MutableList<BannerPojo>) {
        bannerItems.also { bannerImagesList = it }
        notifyDataSetChanged()
    }

    fun clearItem() {
        bannerImagesList.clear()
        notifyDataSetChanged()
    }

//    fun addItem(bannerItem: BannerPojo) {
//        bannerImagesList.add(bannerItem)
//    }


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

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        viewHolder.bind(bannerImagesList[position])
    }

    override fun getCount(): Int = bannerImagesList.size

    inner class SliderAdapterVH(var binding: ItemBannerBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {

        fun bind(pojo: BannerPojo) {
            Picasso.get()
                .load(pojo.image)
                .into(binding.imgBanner)
        }
    }
}
