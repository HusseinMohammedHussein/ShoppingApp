package com.e.commerce.ui.fragments.user.orders.ordersRecyclerView;

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.R
import com.e.commerce.data.model.auth.order.OrderItemPojo
import com.e.commerce.databinding.ItemOrderBinding

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private var orderPojoList: MutableList<OrderItemPojo>? = null

    fun setData(pojos: MutableList<OrderItemPojo>) {
        pojos.also { this.orderPojoList = it }
    }

    inner class OrderViewHolder(var binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        val bundle = Bundle()

        fun bind(orderPojo: OrderItemPojo) {
            binding.tvOrderId.text = String.format("Order ${orderPojo.id}")
            binding.tvDate.text = orderPojo.date
            binding.tvTotalamount.text = String.format("Total Amount: %.2f $", orderPojo.total)
            if (orderPojo.status == "Cancelled") {
                binding.tvOrderStatus.text = orderPojo.status
                binding.tvOrderStatus.setTextColor(Color.RED)
            } else {
                binding.tvOrderStatus.text = orderPojo.status
            }

            binding.tvDetails.setOnClickListener { view ->
                bundle.putParcelable(view.resources.getString(R.string.order_pojo), orderPojo)
                view.findNavController().navigate(R.id.action_orders_to_orderDetails, bundle)   
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        orderPojoList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = orderPojoList!!.size
}