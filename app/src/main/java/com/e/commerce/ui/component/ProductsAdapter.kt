package com.e.commerce.ui.component;

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.commerce.R
import com.e.commerce.data.model.product.ProductPojo
import com.e.commerce.databinding.ItemProductBinding
import com.e.commerce.interfaces.ProductOnClick
import timber.log.Timber
@SuppressLint("NotifyDataSetChanged")
class ProductsAdapter(var context: Context, val onProductClick: ProductOnClick, var productList: ArrayList<ProductPojo>) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    fun clearList() {
        productList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        context = parent.context
        return ProductsViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) = holder.bind(productList[position])

    override fun getItemCount(): Int = productList.size

    inner class ProductsViewHolder(var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val bundle = Bundle()

        @SuppressLint("SetTextI18n")
        fun bind(productPojo: ProductPojo) {
            binding.tvProductName.text = productPojo.name
            binding.tvProductPrice.text = String.format("${productPojo.price} $")
            binding.tvProductOldPrice.text = String.format("${productPojo.old_price} $")
            binding.tvProductOldPrice.paintFlags = STRIKE_THRU_TEXT_FLAG
            binding.tvDiscount.text = String.format("- ${productPojo.discount}%%")
            Timber.d("DiscountAdapter::${productPojo.discount}")

            Glide.with(itemView)
                .load(productPojo.image)
                .into(binding.imgProduct)

            var i = 0
            if (productPojo.in_favorites) {
                binding.btnFavorite.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                i++
            }

            binding.btnFavorite.icFavorite.setOnClickListener {
                if (i == 0) {
                    onProductClick.onAddRemoveProductFavoriteClick(productPojo)
                    binding.btnFavorite.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                    i++
                } else if (i == 1) {
                    onProductClick.onAddRemoveProductFavoriteClick(productPojo)
                    binding.btnFavorite.icFavorite.setImageResource(R.drawable.ic_favorite_disactive)
                    i = 0
                }
            }

            itemView.setOnClickListener { view ->
                bundle.putParcelable(view.context.resources.getString(R.string.product_pojo), productPojo)
                view.findNavController().navigate(R.id.action_product_to_productDetails, bundle)
            }
        }
    }
}