package com.e.commerce.ui.fragments.user.orderDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.data.model.auth.OrderDetailsPojo
import com.e.commerce.data.model.auth.OrderDetailsPojo.OrderProductsPojo
import com.e.commerce.data.model.auth.OrderPojo.OrdersPojo.OrderItemPojo
import com.e.commerce.databinding.FragmentOrderDetailsBinding
import com.e.commerce.ui.main.MainActivity
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import timber.log.Timber

class OrderDetailsFragment : Fragment() {

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private var viewModel: OrderDetailsViewModel = OrderDetailsViewModel()
    private var orderProductsAdapter: OrderProductsAdapter? = null
    private var bundle: Bundle? = null
    private var orderItemPojo: OrderItemPojo? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(OrderDetailsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        initToolbar()
        init()
        getOrderDetails()
    }

    private fun initToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = getString(R.string.orders_fragment)
    }

    private fun init() {
        bundle = Bundle()
        bundle = requireArguments()
        orderProductsAdapter = OrderProductsAdapter()
        orderItemPojo = bundle!!.getParcelable(resources.getString(R.string.order_pojo))
        Timber.d("OrderDetail::${orderItemPojo?.id}")
        binding.rvProducts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = orderProductsAdapter
            visibility = View.GONE
        }
        binding.loading.loading.visibility = View.VISIBLE
    }

    private fun getOrderDetails() {
        orderItemPojo?.id?.let {
            viewModel.getOrderDetails(it).observe(viewLifecycleOwner, { response ->
                if (response.status) {
                    binding.loading.loading.visibility = View.GONE
                    initOrderDetailsViews(response)
                    binding.clOrderDetails.visibility = View.VISIBLE
                } else {
                    binding.loading.loading.visibility = View.GONE
                    binding.clOrderDetails.visibility = View.VISIBLE
                    DynamicToast.makeError(requireContext(), response.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun initOrderDetailsViews(order: OrderDetailsPojo) {
        binding.tvOrderId.text = String.format("Order ${order.data.id}")
        binding.tvDate.text = order.data.date
        binding.tvPaymentMethod.text = order.data.payment_method
        binding.tvStatusOrder.text = order.data.status
        binding.tvTotalProduct.text = String.format("${order.data.products.size} Items")
        binding.tvAddress.text = order.data.address.city
        binding.tvCost.text = String.format("${order.data.cost}$")
        binding.tvVat.text = String.format("${order.data.vat}$")
        binding.tvPoints.text = order.data.points.toString()
        binding.tvPointsCommission.text = order.data.points_commission.toString()
        binding.tvTotalAmount.text = String.format("${order.data.total}$")
        getPromocodeRecentage(order.data.promo_code)
        Timber.d("ProductsOrders::${order.data.products.size}")
        Timber.d("Promocode::${order.data.promo_code}")
        getOrderProducts(order.data.products)
    }

    private fun getOrderProducts(products: ArrayList<OrderProductsPojo>) {
        orderProductsAdapter?.setData(products)
        binding.rvProducts.visibility = View.VISIBLE
    }

    private fun getPromocodeRecentage(promoCode: String) {
        viewModel.getPromocodePercentage(promoCode).observe(viewLifecycleOwner, { response ->
            Timber.d("Promocode::${response.data.percentage}")
            binding.tvDiscount.text = String.format("${response.data.percentage}%%, Personal promo code")
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.d("OrderDetailsBinding::$_binding")
    }
}