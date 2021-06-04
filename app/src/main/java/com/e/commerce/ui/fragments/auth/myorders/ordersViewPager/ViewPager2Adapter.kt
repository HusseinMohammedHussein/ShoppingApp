package com.e.commerce.ui.fragments.auth.myorders.ordersViewPager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.e.commerce.ui.fragments.auth.myorders.ordersRecyclerView.OrdersFragment

// Created by Hussein_Mohammad on 6/3/2021.
class ViewPager2Adapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                OrdersFragment()
            }

            1 -> {
                OrdersFragment()
            }
        }
        return OrdersFragment()
    }
}