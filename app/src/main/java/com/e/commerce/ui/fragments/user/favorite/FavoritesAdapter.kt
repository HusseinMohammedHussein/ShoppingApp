package com.e.commerce.ui.fragments.user.favorite;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.R
import com.e.commerce.data.model.auth.FavoritePojo.FavoriteResponse.FavoritesResponse
import com.e.commerce.databinding.ItemFavoriteBinding
import com.squareup.picasso.Picasso
import timber.log.Timber


class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
    private lateinit var favoritesPojoList: MutableList<FavoritesResponse>
    lateinit var onItemClick: ((FavoritesResponse) -> Unit)

    fun setData(favoritesPojos: MutableList<FavoritesResponse>) {
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

            // favorite_products Response Data without in_cart field___________________________!!
            Timber.d("Product::${pojo.product.name} is in cart ${pojo.product.in_cart}")
            if (pojo.product.in_cart) {
                Picasso.get()
                    .load(R.drawable.ic_in_cart)
                    .into(binding.icInCart)
            } else {
                binding.icInCart.visibility = ViewGroup.GONE
            }
            //__________________________________________________________________________________//

            binding.icRemoveItem.setOnClickListener { onItemClick.invoke(pojo); removeItem(index) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val favoritesPojos: FavoritesResponse = favoritesPojoList[position]
        holder.bind(favoritesPojos, position)
    }

    override fun getItemCount(): Int = favoritesPojoList.size
}