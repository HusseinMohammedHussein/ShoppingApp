package com.e.commerce.ui.fragments.user.orderDetails;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.data.model.auth.OrderDetailsPojo.OrderProductsPojo
import com.e.commerce.databinding.ItemOrderProductBinding
import com.squareup.picasso.Picasso

class OrderProductsAdapter() : RecyclerView.Adapter<OrderProductsAdapter.OrderProductsViewHolder>() {

    private lateinit var orderProductsList: MutableList<OrderProductsPojo>

    fun setData(orderProductPojo: MutableList<OrderProductsPojo>) {
        orderProductPojo.also { this.orderProductsList = it }
    }

    inner class OrderProductsViewHolder(var binding: ItemOrderProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderProductPojo: OrderProductsPojo) {
            binding.tvProductName.text = orderProductPojo.name
            binding.tvProductQuantity.text = orderProductPojo.quantity.toString()
            binding.tvProductPrice.text = String.format("${orderProductPojo.price}$")
            Picasso.get().load(orderProductPojo.image).into(binding.imgProductOrder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductsViewHolder {
        return OrderProductsViewHolder(ItemOrderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OrderProductsViewHolder, position: Int) {
        holder.bind(orderProductsList[position])
    }

    override fun getItemCount(): Int = if (orderProductsList.isNotEmpty()) orderProductsList.size else 0
}