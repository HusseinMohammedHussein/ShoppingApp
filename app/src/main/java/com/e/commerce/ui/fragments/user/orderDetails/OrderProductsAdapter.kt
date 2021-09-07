package com.e.commerce.ui.fragments.user.orderDetails;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.commerce.data.model.auth.OrderDetailsPojo.OrderProductsPojo
import com.e.commerce.databinding.ItemOrderProductBinding

class OrderProductsAdapter : RecyclerView.Adapter<OrderProductsAdapter.OrderProductsViewHolder>() {

    private var orderProductsList = ArrayList<OrderProductsPojo>()

    fun setData(orderProductPojo: ArrayList<OrderProductsPojo>) {
        orderProductPojo.also { this.orderProductsList = it }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductsViewHolder {
        return OrderProductsViewHolder(ItemOrderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OrderProductsViewHolder, position: Int) {
        holder.bind(orderProductsList[position])
    }

    override fun getItemCount(): Int = orderProductsList.size


    inner class OrderProductsViewHolder(var binding: ItemOrderProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderProductPojo: OrderProductsPojo) {
            binding.tvProductName.text = orderProductPojo.name
            binding.tvProductQuantity.text = orderProductPojo.quantity.toString()
            binding.tvProductPrice.text = String.format("${orderProductPojo.price}$")
            Glide.with(itemView).load(orderProductPojo.image).into(binding.imgProductOrder)
        }
    }
}