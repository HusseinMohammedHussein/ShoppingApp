package com.e.commerce.ui.common;

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.R
import com.e.commerce.data.model.ProductPojo
import com.e.commerce.databinding.ItemProductBinding
import com.squareup.picasso.Picasso
import timber.log.Timber

class ProductsAdapter(private val onProductClick: ProductOnClick) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private var pojoList = ArrayList<ProductPojo>()
    private var context: Context? = null

    fun setData(pojos: ArrayList<ProductPojo>) {
        pojos.also { this.pojoList = it }
    }

    fun clearList() {
        pojoList.let {
            pojoList.clear()
            notifyDataSetChanged()
        }
    }

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

            Picasso.get()
                .load(productPojo.image)
                .into(binding.imgProduct)

            var i = 0
            if (productPojo.in_favorites) {
                binding.btnFavorite.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                i++
            }

            binding.btnFavorite.icFavorite.setOnClickListener {
                if (i == 0) {
//                    onItemClick.invoke(pojo)
                    onProductClick.onProductItemClick(productPojo)
                    binding.btnFavorite.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                    i++
                } else if (i == 1) {
                    onProductClick.onProductItemClick(productPojo)
//                    onItemClick.invoke(productPojo)
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

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(pojoList[position])
    }

    override fun getItemCount(): Int = pojoList.size

    interface ProductOnClick {
        fun onProductItemClick(product: ProductPojo)
    }
}