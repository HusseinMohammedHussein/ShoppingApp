package com.e.commerce.ui.fragments.productdetails;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.databinding.ItemProductImageBinding
import com.squareup.picasso.Picasso

class ProductImageAdapter : RecyclerView.Adapter<ProductImageAdapter.ProductImageViewHolder>() {

    private lateinit var pojoList: List<String>

    fun setData(pojos: List<String>) {
        pojos.also { this.pojoList = it }
    }

    inner class ProductImageViewHolder(var binding: ItemProductImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pojo: String) {
            Picasso.get()
                .load(pojo)
                .into(binding.imgProImage)
        }
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

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        holder.bind(pojoList[position])
    }

    override fun getItemCount(): Int = pojoList.size
}