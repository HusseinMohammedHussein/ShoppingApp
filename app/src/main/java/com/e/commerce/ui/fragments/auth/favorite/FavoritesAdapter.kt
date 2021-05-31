package com.e.commerce.ui.fragments.auth.favorite;

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.R
import com.e.commerce.data.model.auth.FavoritePojo.FavoriteResponse.FavoritesResponse
import com.e.commerce.databinding.ItemFavoriteBinding
import com.squareup.picasso.Picasso


class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private lateinit var pojoList: MutableList<FavoritesResponse>
    lateinit var onItemClick: ((FavoritesResponse) -> Unit)
    private lateinit var context: Context

    fun setData(pojos: MutableList<FavoritesResponse>) {
        pojos.also { this.pojoList = it }
    }

    fun clearData() {
        if (pojoList.size > 0) {
            pojoList.clear()
            notifyDataSetChanged()
        }
    }

    fun removeItem(index: Int) {
        pojoList.removeAt(index)
        notifyDataSetChanged()
    }

    inner class FavoritesViewHolder(var binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pojo: FavoritesResponse, index: Int) {
            binding.tvProductName.text = pojo.product.name
            binding.tvProductPrice.text = String.format("${pojo.product.price} $")
            binding.tvProductOldPrice.text = String.format("${pojo.product.old_price} $")
            binding.tvDiscount.text = String.format("- ${pojo.product.discount}%%")


            Picasso.get()
                .load(pojo.product.image)
                .into(binding.imgProduct)

            if (pojo.product.in_cart) {
                Picasso.get()
                    .load(R.drawable.ic_in_cart)
                    .into(binding.icInCart)
            }


            binding.icRemoveItem.setOnClickListener {
                onItemClick.invoke(pojo)
                removeItem(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        context = parent.context
        return FavoritesViewHolder(ItemFavoriteBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(pojoList[position], position)
    }

    override fun getItemCount(): Int = if (pojoList.isNotEmpty()) pojoList.size else 0
}