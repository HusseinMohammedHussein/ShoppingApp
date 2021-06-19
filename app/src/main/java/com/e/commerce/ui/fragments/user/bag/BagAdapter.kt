package com.e.commerce.ui.fragments.user.bag

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.R
import com.e.commerce.data.model.auth.common.BagItemResponsePojo
import com.e.commerce.databinding.ItemBagBinding
import com.e.commerce.util.NewQuantity
import com.squareup.picasso.Picasso
import timber.log.Timber

class BagAdapter(private var newQuantity: NewQuantity) : RecyclerView.Adapter<BagAdapter.BagViewHolder>() {

    private var pojoList: MutableList<BagItemResponsePojo>? = null

    lateinit var onAddRemoveFavoriteClick: ((BagItemResponsePojo) -> Unit)
    lateinit var onRemoveFromBagClick: ((BagItemResponsePojo) -> Unit)
    lateinit var context: Context

    fun setData(pojos: MutableList<BagItemResponsePojo>) {
        pojos.also { this.pojoList = it }
        notifyDataSetChanged()
    }

    fun clearData() {
        pojoList?.clear()
        notifyDataSetChanged()
    }

    fun removeProduct(index: Int) {
        pojoList?.removeAt(index)
        notifyDataSetChanged()
    }

    inner class BagViewHolder(var binding: ItemBagBinding) : RecyclerView.ViewHolder(binding.root) {
        var isAddedToFavorite: Boolean = false

        fun bind(bagPojo: BagItemResponsePojo, index: Int) {
                binding.tvProductName.text = bagPojo.product.name
                binding.tvProductPrice.text = String.format("${bagPojo.product.price}$")
                binding.tvProductQuantity.text = bagPojo.quantity.toString()

                Picasso.get()
                    .load(bagPojo.product.image)
                    .into(binding.imgProduct)

                binding.cvMinus.isEnabled = bagPojo.quantity != 1

                binding.cvPlus.setOnClickListener {
                    bagPojo.quantity++
                    newQuantity.newQuantity(bagPojo.id, bagPojo.quantity)
                    notifyDataSetChanged()
                    Timber.d("quantityAfterUpdate_Plus is ${bagPojo.quantity}")
                }

                binding.cvMinus.setOnClickListener {
                    bagPojo.quantity--
                    newQuantity.newQuantity(bagPojo.id, bagPojo.quantity)
                    notifyDataSetChanged()
                    Timber.d("quantityAfterUpdate_Minus is ${bagPojo.quantity}")
                }


                val popupMenu = PopupMenu(itemView.context, binding.icThreeDots)
                popupMenu.inflate(R.menu.bag_menu)

                if (bagPojo.product.in_favorites) {
                    isAddedToFavorite = true
                    popupMenu.menu.getItem(0).title = "Remove From Favorites"
                }

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.add_to_favorite -> {
                            onAddRemoveFavoriteClick.invoke(bagPojo)
                            isAddedToFavorite = true
                            return@setOnMenuItemClickListener true
                        }
                        R.id.remove_from_favorite -> {
                            onAddRemoveFavoriteClick.invoke(bagPojo)
                            isAddedToFavorite = false
                            return@setOnMenuItemClickListener true
                        }

                        R.id.delete -> {
                            onRemoveFromBagClick.invoke(bagPojo)
                            removeProduct(index)
                            return@setOnMenuItemClickListener true
                        }
                    }
                    return@setOnMenuItemClickListener false
                }

                binding.icThreeDots.setOnClickListener {
                    Timber.d("isAddedToFavorite::${isAddedToFavorite}")
                    if (isAddedToFavorite) {
                        popupMenu.menu.getItem(0).isVisible = false
                        popupMenu.menu.getItem(1).isVisible = true
                    } else {
                        popupMenu.menu.getItem(0).isVisible = true
                        popupMenu.menu.getItem(1).isVisible = false
                    }
                    popupMenu.show()
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagViewHolder {
        context = parent.context
        return BagViewHolder(ItemBagBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: BagViewHolder, position: Int) {
        val bagItems: BagItemResponsePojo? = pojoList?.get(position)
        bagItems?.let { holder.bind(it, position) }
    }

    override fun getItemCount(): Int = pojoList?.size!!
}