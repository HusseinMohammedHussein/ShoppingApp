package com.e.commerce.ui.fragments.categorydetails;

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.commerce.R
import com.e.commerce.data.model.product.ProductPojo
import com.e.commerce.databinding.ItemCategoryProductsBinding
import com.e.commerce.interfaces.OnCategoryProductClick
import timber.log.Timber

@SuppressLint("NotifyDataSetChanged")
class ProductsCategoryAdapter(val onCategoryProductClick: OnCategoryProductClick) :
    RecyclerView.Adapter<ProductsCategoryAdapter.CategoryProductsViewHolder>() {

    private var products = ArrayList<ProductPojo>()

    fun setData(products: ArrayList<ProductPojo>) {
        this.products = products
    }

    fun clearData() {
        products.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductsViewHolder {
        return CategoryProductsViewHolder(
            ItemCategoryProductsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryProductsViewHolder, position: Int) = holder.bind(products[position])

    override fun getItemCount(): Int = products.size


    inner class CategoryProductsViewHolder(var binding: ItemCategoryProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productPojo: ProductPojo) {
            binding.tvProductName.text = productPojo.name
            binding.tvProductPrice.text = String.format("${productPojo.price} $")
            binding.tvProductOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.tvDiscount.text = String.format("- ${productPojo.discount}%%")

            if (productPojo.old_price == 0.0) {
                binding.tvProductOldPrice.visibility = View.GONE
                Timber.d("Discount::${productPojo.old_price}")
            } else {
                binding.tvProductOldPrice.text = String.format("${productPojo.old_price} $")
            }

            var i = 0
            if (productPojo.in_favorites) {
                binding.favorite.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                i++
            }

            binding.favorite.icFavorite.setOnClickListener {
                if (i == 0) {
                    onCategoryProductClick.onItemClick(productPojo)
                    binding.favorite.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                    i++
                } else if (i == 1) {
                    onCategoryProductClick.onItemClick(productPojo)
                    binding.favorite.icFavorite.setImageResource(R.drawable.ic_favorite_disactive)
                    i = 0
                }
            }

            Glide.with(itemView)
                .load(productPojo.image)
                .into(binding.imgProduct)

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(it.context.resources.getString(R.string.product_pojo), productPojo)
                Timber.d("productIdItem::${bundle.getInt(it.context.resources.getString(R.string.product_pojo))}")
                itemView.findNavController()
                    .navigate(R.id.action_product_to_productDetails, bundle)
            }
        }
    }
}