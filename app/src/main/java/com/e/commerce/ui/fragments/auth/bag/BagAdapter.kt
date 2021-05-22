package com.e.commerce.ui.fragments.auth.bag

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

class BagAdapter(var newQuantity: NewQuantity) : RecyclerView.Adapter<BagAdapter.BagViewHolder>() {

    private lateinit var pojoList: List<BagItemResponsePojo>

    fun setData(pojos: List<BagItemResponsePojo>) {
        pojos.also { this.pojoList = it }
    }

    inner class BagViewHolder(var binding: ItemBagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bagPojo: BagItemResponsePojo) {
            binding.tvProductName.text = bagPojo.product.name
            binding.tvProductPrice.text = String.format("${bagPojo.product.price} $")
            binding.tvProductQuantity.text = bagPojo.quantity.toString()

            Picasso.get()
                .load(bagPojo.product.image)
                .into(binding.imgProduct)

            Timber.d("quantityBeforeUpdate is ${bagPojo.quantity}")

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

            binding.icThreeDots.setOnClickListener {
                val popup = PopupMenu(itemView.context, binding.icThreeDots)
                popup.inflate(R.menu.bag_menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.add_to_favorite -> {
                            Timber.d("Item Added ${bagPojo.id}")
                            return@setOnMenuItemClickListener true
                        }

                        R.id.delete -> {
                            Timber.d("Item Deleted ${bagPojo.id}")
                            return@setOnMenuItemClickListener true
                        }

                        else -> false
                    }
                }
                popup.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagViewHolder {
        return BagViewHolder(ItemBagBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BagViewHolder, position: Int) {
        holder.bind(pojoList[position])
    }

    override fun getItemCount(): Int = if (pojoList.isNotEmpty()) pojoList.size else 0
}