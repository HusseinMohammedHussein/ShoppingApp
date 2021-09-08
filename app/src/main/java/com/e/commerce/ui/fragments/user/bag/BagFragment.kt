package com.e.commerce.ui.fragments.user.bag

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.data.model.auth.bag.BagItemPojo
import com.e.commerce.databinding.FragmentBagBinding
import com.e.commerce.interfaces.AddRemoveBagItemInterface
import com.e.commerce.interfaces.NewQuantityInterface
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import timber.log.Timber
import kotlin.collections.set

@SuppressLint("NotifyDataSetChanged")
class BagFragment : Fragment() {
    private var _binding: FragmentBagBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BagViewModel
    private lateinit var bagAdapter: BagAdapter
    private lateinit var sharedPref: SharedPref

    private val hashMap: HashMap<String, Int> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(BagViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        setupToolbar()
        init()
        request()
        responseData()
        onClick()
    }

    private fun request() {
        viewModel.getBag()
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = getString(R.string.bag_fragment)
    }

    private fun init() {
        sharedPref = SharedPref(requireContext())
        bagAdapter = BagAdapter(requireContext(), newQuantity, onAddRemoveFavoriteClick)
        binding.loading.loading.visibility = View.VISIBLE
        binding.srContent.isRefreshing = false

        binding.srContent.setOnRefreshListener {
            viewModel.getBag()
            bagAdapter.notifyDataSetChanged()
        }

        binding.content.rvBag.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = bagAdapter
        }
    }

    private fun responseData() {
        viewModel.bagMutableData.observe(viewLifecycleOwner, { response ->
            binding.srContent.isRefreshing = false
            binding.loading.loading.visibility = View.GONE
            if (response.status) {
                binding.noauth.tvNoAuthMsg.visibility = View.GONE
                binding.noauth.tvNoAuthMsg.text

                bagAdapter.setData(response.bagProductsDataData.bagItems)
                Timber.d("BagProductsSize:: ${response.bagProductsDataData.bagItems.size}")
                binding.tvTotalamount.text = String.format("${response.bagProductsDataData.total}$")
                sharedPref.setDouble(getString(R.string.total_amount), response.bagProductsDataData.total)
                bagAdapter.notifyDataSetChanged()

                binding.etPromocode.isEnabled = true
                binding.vNotHasProducts.visibility = View.GONE
                binding.content.rvBag.visibility = View.VISIBLE
            } else {
                binding.vNotHasProducts.visibility = View.VISIBLE
                binding.noauth.tvNoAuthMsg.visibility = View.VISIBLE
                binding.noauth.tvNoAuthMsg.text = response.message
                binding.etPromocode.isEnabled = false
                binding.vNotHasProducts.isFocusable = false
                binding.content.rvBag.visibility = View.GONE
            }
        })

        viewModel.quantityBagUpdateMutableData.observe(viewLifecycleOwner, { bagUpdateResponse ->
            Timber.d("BagUpdatedResponse:: ${bagUpdateResponse.bagUpdateData.bag.productItemQuantity}")
            binding.tvTotalamount.text = String.format("${bagUpdateResponse.bagUpdateData.total}$")
            bagAdapter.notifyDataSetChanged()
        })
    }

    private val newQuantity = object : NewQuantityInterface {
        override fun newQuantity(cartId: Int, quantity: Int) {
            hashMap["quantity"] = quantity
            viewModel.updateQuantityBag(cartId, hashMap)
            Timber.d("cartId::${cartId} && Quantity::${quantity}")
        }
    }

    private val onAddRemoveFavoriteClick = object : AddRemoveBagItemInterface {
        override fun onAddRemoveFavoriteBagItem(bagPojo: BagItemPojo) {
            binding.srContent.isRefreshing = true
            viewModel.addRemoveFavorite(bagPojo.product.id).observe(viewLifecycleOwner, { response ->
                binding.srContent.isRefreshing = false
                DynamicToast.makeSuccess(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                viewModel.getBag()
            })
            bagAdapter.notifyDataSetChanged()
        }

        override fun onAddRemoveBagItem(bagPojo: BagItemPojo) {
            viewModel.removeBagProduct(bagPojo.product.id).observe(viewLifecycleOwner, { response ->
                binding.srContent.isRefreshing = false
                DynamicToast.makeSuccess(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                viewModel.getBag()
            })
            bagAdapter.notifyDataSetChanged()
        }
    }

    private fun onClick() {
        binding.btnCheckOut.setOnClickListener {
            val getCodeText = binding.etPromocode.text.toString().trim()
            viewModel.checkPromoCode(getCodeText).observe(viewLifecycleOwner, { response ->
                if (response.status) {
                    sharedPref.setInt(getString(R.string.promocode_id), response.proCodeData.id)
                    findNavController().navigate(R.id.action_bag_to_checkout)
                } else {
                    DynamicToast.makeError(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        binding.srContent.isRefreshing = false
        viewModel.getBag()
        bagAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.d("BagFragmentBinding::${_binding}")
    }
}