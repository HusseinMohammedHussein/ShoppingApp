package com.e.commerce.ui.fragments.auth.myorders.ordersRecyclerView;

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.R
import com.e.commerce.data.model.auth.OrderPojo
import com.e.commerce.data.model.auth.OrderPojo.OrdersPojo.OrderItemPojo
import com.e.commerce.databinding.ItemOrderBinding

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var pojoList: MutableList<OrderItemPojo>? = null
    lateinit var onItemClick: ((OrderPojo) -> Unit)

    fun setData(pojos: MutableList<OrderItemPojo>) {
        pojos.also { this.pojoList = it }
    }

    fun clearData() {
        if (pojoList?.isNotEmpty() == true) {
            pojoList?.clear()
            notifyDataSetChanged()
        }
    }

    inner class OrderViewHolder(var binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderPojo: OrderItemPojo) {
            binding.tvOrderId.text = String.format("Order ${orderPojo.id}")
            binding.tvDate.text = orderPojo.date
            binding.tvTotalamount.text = String.format("Total Amount: ${orderPojo.total}")
            if (orderPojo.status == "Cancelled") {
                binding.tvOrderStatus.text = orderPojo.status
                binding.tvOrderStatus.setTextColor(Color.RED)
            } else {
                binding.tvOrderStatus.text = orderPojo.status
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        pojoList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = pojoList!!.size
}