package com.e.commerce.ui.fragments.user.bag

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.commerce.R
import com.e.commerce.data.model.auth.bag.BagItemPojo
import com.e.commerce.databinding.ItemBagBinding
import com.e.commerce.interfaces.AddRemoveBagItemInterface
import com.e.commerce.interfaces.NewQuantityInterface
import timber.log.Timber

@SuppressLint("NotifyDataSetChanged")
class BagAdapter(
    var context: Context,
    var newQuantityInterface: NewQuantityInterface,
    var bagItemInterface: AddRemoveBagItemInterface
) :
    RecyclerView.Adapter<BagAdapter.BagViewHolder>() {

    private var bagItems = ArrayList<BagItemPojo>()

    fun setData(bagItems: ArrayList<BagItemPojo>) {
        this.bagItems = bagItems
    }

    fun removeProduct(index: Int) {
        bagItems.removeAt(index)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagViewHolder {
        context = parent.context
        return BagViewHolder(ItemBagBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: BagViewHolder, position: Int) = holder.bind(bagItems[position], position)

    override fun getItemCount(): Int = bagItems.size

    inner class BagViewHolder(var binding: ItemBagBinding) : RecyclerView.ViewHolder(binding.root) {

        var isAddedToFavorite: Boolean = false
        fun bind(bagPojo: BagItemPojo, index: Int) {
            binding.tvProductName.text = bagPojo.product.name
            binding.tvProductPrice.text = String.format("${bagPojo.product.price}$")
            binding.tvProductQuantity.text = bagPojo.productItemQuantity.toString()

            Glide.with(itemView)
                .load(bagPojo.product.image)
                .into(binding.imgProduct)

            binding.cvMinus.isEnabled = bagPojo.productItemQuantity != 1

            binding.cvPlus.setOnClickListener {
                bagPojo.productItemQuantity++
                newQuantityInterface.newQuantity(bagPojo.id, bagPojo.productItemQuantity)
                notifyDataSetChanged()
                Timber.d("quantityAfterUpdate_Plus is ${bagPojo.productItemQuantity}")
            }

            binding.cvMinus.setOnClickListener {
                bagPojo.productItemQuantity--
                newQuantityInterface.newQuantity(bagPojo.id, bagPojo.productItemQuantity)
                notifyDataSetChanged()
                Timber.d("quantityAfterUpdate_Minus is ${bagPojo.productItemQuantity}")
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
                        bagItemInterface.onAddRemoveFavoriteBagItem(bagPojo)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.remove_from_favorite -> {
                        bagItemInterface.onAddRemoveFavoriteBagItem(bagPojo)
                        isAddedToFavorite = false
                        return@setOnMenuItemClickListener true
                    }

                    R.id.delete -> {
                        bagItemInterface.onAddRemoveBagItem(bagPojo)
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
}