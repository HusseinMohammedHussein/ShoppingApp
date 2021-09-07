package com.e.commerce.ui.fragments.productdetails;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.commerce.databinding.ItemProductImageBinding

class ProductImageAdapter : RecyclerView.Adapter<ProductImageAdapter.ProductImageViewHolder>() {

    private var pojoList = ArrayList<String>()

    fun setData(pojos: ArrayList<String>) {
        pojos.also { this.pojoList = it }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        return ProductImageViewHolder(
            ItemProductImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) = holder.bind(pojoList[position])

    override fun getItemCount(): Int = pojoList.size

    inner class ProductImageViewHolder(var binding: ItemProductImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pojo: String) {
            Glide.with(itemView)
                .load(pojo)
                .into(binding.imgProImage)
        }
    }
}