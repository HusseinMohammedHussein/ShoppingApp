package com.e.commerce.ui.fragments.auth.bag

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.databinding.FragmentBagBinding
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.NewQuantity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BagFragment : Fragment(), NewQuantity {
    private lateinit var binding: FragmentBagBinding
    private var viewModel: BagViewModel = BagViewModel()
    private val bagAdapter: BagAdapter = BagAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BagViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        setupToolbar()
        init()
        observerData()
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { (requireActivity() as MainActivity).onBackPressed() }
        binding.appbar.tvTitle.text = "My Bag"
    }

    private fun init() {
        binding.loading.loading.visibility = View.VISIBLE
        binding.content.rvBag.visibility = View.GONE

        binding.content.rvBag.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = bagAdapter
        }
    }

    private fun observerData() {
        viewModel.getBag()
        viewModel.bagData.observe(viewLifecycleOwner, { bagDataResponse ->
            binding.loading.loading.visibility = View.GONE
            binding.content.rvBag.visibility = View.VISIBLE
            bagAdapter.setData(bagDataResponse.bagResponseData.cartItems)
            Timber.d("TotalOfProducts::${bagDataResponse.bagResponseData.total}")
            bagAdapter.notifyDataSetChanged()

            binding.tvTotalamount.text = String.format("${bagDataResponse.bagResponseData.total}$")
        })

        viewModel.updateBagData.observe(viewLifecycleOwner, { bagUpdateResponse ->
            Timber.d("BagUpdatedResponse:: ${bagUpdateResponse.data.cart.quantity}")
            binding.tvTotalamount.text = String.format("${bagUpdateResponse.data.total}$")
        })
//        bagAdapter.setNewQuantity(object : BagAdapter.SetNewQuantity {override fun newQuantity(cartId: Int, quantity: Int) {}})
    }

    override fun newQuantity(cartId: Int, quantity: Int) {
        val hashMap: HashMap<String, Int> = HashMap()
        hashMap["quantity"] = quantity
        viewModel.updateBag(cartId, hashMap)
        Timber.d("cartId::${cartId} && Quantity::${quantity}")
    }
}