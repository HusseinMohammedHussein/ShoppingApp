package com.e.commerce.ui.fragments.categorydetails;

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.R
import com.e.commerce.data.model.ProductPojo
import com.e.commerce.databinding.ItemCategoryProductsBinding
import com.squareup.picasso.Picasso
import timber.log.Timber

class ProductsCategoryAdapter :
    RecyclerView.Adapter<ProductsCategoryAdapter.CategoryProductsViewHolder>() {

    private lateinit var pojoList: MutableList<ProductPojo>
    lateinit var onItemClick: ((ProductPojo) -> Unit)

    fun setData(pojos: MutableList<ProductPojo>) {
        pojos.also { this.pojoList = it }
    }

    fun clearData() {
        pojoList.clear()
        notifyDataSetChanged()
    }

    inner class CategoryProductsViewHolder(var binding: ItemCategoryProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pojo: ProductPojo) {
            binding.tvProductName.text = pojo.name
            binding.tvProductPrice.text = String.format("${pojo.price} $")
            binding.tvProductOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.tvDiscount.text = String.format("- ${pojo.discount}%%")

            if (pojo.old_price == 0.0) {
                binding.tvProductOldPrice.visibility = View.GONE
                Timber.d("Discount::${pojo.old_price}")
            } else {
                binding.tvProductOldPrice.text = String.format("${pojo.old_price} $")
            }

            var i = 0
            if (pojo.in_favorites) {
                binding.favorite.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                i++
            }

            binding.favorite.icFavorite.setOnClickListener {
                if (i == 0) {
                    onItemClick.invoke(pojo)
                    binding.favorite.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                    i++
                } else if (i == 1) {
                    onItemClick.invoke(pojo)
                    binding.favorite.icFavorite.setImageResource(R.drawable.ic_favorite_disactive)
                    i = 0
                }
            }

            Picasso.get()
                .load(pojo.image)
                .into(binding.imgProduct)

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(it.context.resources.getString(R.string.product_pojo), pojo)
                Timber.d("productIdItem::${bundle.getInt(it.context.resources.getString(R.string.product_pojo))}")
                itemView.findNavController()
                    .navigate(R.id.action_product_to_productDetails, bundle)
            }
        }
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

    override fun onBindViewHolder(holder: CategoryProductsViewHolder, position: Int) {
        holder.bind(pojoList[position])
    }

    override fun getItemCount(): Int = pojoList.size
}