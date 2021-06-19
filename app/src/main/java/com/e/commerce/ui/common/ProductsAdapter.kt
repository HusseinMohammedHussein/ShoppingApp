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

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private lateinit var pojoList: MutableList<ProductPojo>
    private lateinit var context: Context
    lateinit var onItemClick: ((ProductPojo) -> Unit)

    fun setData(pojos: MutableList<ProductPojo>) {
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
        fun bind(pojo: ProductPojo) {
            binding.tvProductName.text = pojo.name
            binding.tvProductPrice.text = String.format("${pojo.price} $")
            binding.tvProductOldPrice.text = String.format("${pojo.old_price} $")
            binding.tvProductOldPrice.paintFlags = STRIKE_THRU_TEXT_FLAG
            binding.tvDiscount.text = String.format("- ${pojo.discount}%%")
            Timber.d("DiscountAdapter::${pojo.discount}")

            Picasso.get()
                .load(pojo.image)
                .into(binding.imgProduct)

            var i = 0
            if (pojo.in_favorites) {
                binding.btnFavorite.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                i++
            }

            binding.btnFavorite.icFavorite.setOnClickListener {
                if (i == 0) {
                    onItemClick.invoke(pojo)
                    binding.btnFavorite.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                    i++
                } else if (i == 1) {
                    onItemClick.invoke(pojo)
                    binding.btnFavorite.icFavorite.setImageResource(R.drawable.ic_favorite_disactive)
                    i = 0
                }
            }

            itemView.setOnClickListener { view ->
                bundle.putParcelable(view.context.resources.getString(R.string.product_pojo), pojo)
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
}