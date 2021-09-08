package com.e.commerce.ui.fragments.user.favorite;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.commerce.R
import com.e.commerce.data.model.auth.favorite.FavoritesDataPojo.FavoriteDataPojo
import com.e.commerce.databinding.ItemFavoriteBinding
import timber.log.Timber


class FavoritesAdapter(private val onFavoriteClick: FavoriteItemClick) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
    private var favoritesPojoList = ArrayList<FavoriteDataPojo>()

    fun setData(favoritesPojos: ArrayList<FavoriteDataPojo>) {
        favoritesPojos.also { this.favoritesPojoList = it }
    }

    fun clearData() {
        if (favoritesPojoList.size > 0) {
            favoritesPojoList.clear()
            notifyDataSetChanged()
        }
    }

    fun removeItem(index: Int) {
        favoritesPojoList.removeAt(index)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val favoritesPojos: FavoriteDataPojo = favoritesPojoList[position]
        holder.bind(favoritesPojos, position)
    }

    override fun getItemCount(): Int = favoritesPojoList.size


    inner class FavoritesViewHolder(var binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoritesPojo: FavoriteDataPojo, index: Int) {
            binding.tvProductName.text = favoritesPojo.product.name
            binding.tvProductPrice.text = String.format("${favoritesPojo.product.price} $")
            binding.tvProductOldPrice.text = String.format("${favoritesPojo.product.old_price} $")
            binding.tvDiscount.text = String.format("- ${favoritesPojo.product.discount}%%")
            Glide.with(itemView)
                .load(favoritesPojo.product.image)
                .into(binding.imgProduct)

            // favorite_products Response Data without in_cart field___________________________!!
            Timber.d("Product::${favoritesPojo.product.name} is in cart ${favoritesPojo.product.in_cart}")
            if (favoritesPojo.product.in_cart) {
                Glide.with(itemView)
                    .load(R.drawable.ic_in_cart)
                    .into(binding.icInCart)
            } else {
                binding.icInCart.visibility = ViewGroup.GONE
            }
            //__________________________________________________________________________________//

            binding.icRemoveItem.setOnClickListener {
                onFavoriteClick.onFavoriteItemClick(favoritesPojo)
                removeItem(index)
            }
        }
    }

    interface FavoriteItemClick {
        fun onFavoriteItemClick(favorite: FavoriteDataPojo)
    }
}