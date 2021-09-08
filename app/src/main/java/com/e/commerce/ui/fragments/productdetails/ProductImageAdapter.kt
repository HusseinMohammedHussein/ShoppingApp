package com.e.commerce.ui.fragments.productdetails;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.commerce.databinding.ItemProductImageBinding

class ProductImageAdapter(var productImages: ArrayList<String>) : RecyclerView.Adapter<ProductImageAdapter.ProductImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        return ProductImageViewHolder(
            ItemProductImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) = holder.bind(productImages[position])

    override fun getItemCount(): Int = productImages.size

    inner class ProductImageViewHolder(var binding: ItemProductImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pojo: String) {
            Glide.with(itemView)
                .load(pojo)
                .into(binding.imgProImage)
        }
    }
}