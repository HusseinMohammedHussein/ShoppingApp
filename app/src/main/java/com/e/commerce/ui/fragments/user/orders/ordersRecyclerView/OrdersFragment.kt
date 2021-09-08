package com.e.commerce.ui.fragments.user.orders.ordersRecyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.databinding.FragmentOrdersBinding
import com.e.commerce.ui.main.MainActivity
import timber.log.Timber

class OrdersFragment : Fragment() {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: OrdersViewModel
    private lateinit var ordersAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(OrdersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        initToolbar()
        init()
        request()
        observerData()
    }

    private fun init() {
        viewModel = OrdersViewModel()
        ordersAdapter = OrderAdapter()
        binding.rvStateOrderNew.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ordersAdapter
            visibility = View.GONE
        }
    }

    private fun request() {
        viewModel.getOrders()
    }

    private fun observerData() {
        viewModel.ordersMutableLiveData.observe(viewLifecycleOwner, { response ->
            ordersAdapter.setData(response.orderData.ordersData)
            binding.rvStateOrderNew.visibility = View.VISIBLE
        })
    }

    private fun initToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.d("OrdersBinding::$_binding")
    }
}