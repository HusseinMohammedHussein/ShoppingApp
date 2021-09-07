package com.e.commerce.ui.fragments.user.orders.ordersViewPager_notwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.e.commerce.databinding.FragmentOrdersViewpagerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import timber.log.Timber

class OrdersViewPager : Fragment() {
    private var _binding: FragmentOrdersViewpagerBinding? = null
    private val binding get() = _binding!!
//    private val orderAdapter = OrderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersViewpagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        val viewPager2Adapter = ViewPager2Adapter(this)
        binding.vpMyOrders.adapter = viewPager2Adapter
        binding.tabsStatus.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        tab.text = "New"
                        Timber.d("CLick ${tab.position}")
                    }
                    1 -> {
                        tab.text = "Delivery"
//                        orderAdapter.clearData()
                        Timber.d("CLick ${tab.position}")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        TabLayoutMediator(binding.tabsStatus, binding.vpMyOrders) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "New"
                }

                1 -> {
                    tab.text = "Delivery"
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.d("OrdersBindingIs::$_binding")
    }
}
