package com.e.commerce.ui.fragments.user.bag

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
import com.e.commerce.databinding.FragmentBagBinding
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.NewQuantity
import com.e.commerce.util.SharedPref
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.collections.set

@AndroidEntryPoint
class BagFragment : Fragment() {
    private var _binding: FragmentBagBinding? = null
    private val binding get() = _binding!!
    private var viewModel: BagViewModel = BagViewModel()
    private var bagAdapter: BagAdapter? = null
    private val hashMap: HashMap<String, Int> = HashMap()
    private var sharedPref: SharedPref? = null

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
        bagAdapter = BagAdapter(newQuantity)
        sharedPref = SharedPref(requireContext())
        binding.loading.loading.visibility = View.VISIBLE
        binding.srContent.isRefreshing = false

        binding.srContent.setOnRefreshListener {
            viewModel.getBag()
            bagAdapter?.notifyDataSetChanged()
        }

        binding.content.rvBag.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = bagAdapter

        }
    }

    private val newQuantity = object : NewQuantity {
        override fun newQuantity(cartId: Int, quantity: Int) {
            hashMap["quantity"] = quantity
            viewModel.updateQuantityBag(cartId, hashMap)
            Timber.d("cartId::${cartId} && Quantity::${quantity}")
        }
    }

    private fun responseData() {
        viewModel.bagMutableData.observe(viewLifecycleOwner, { bagDataResponse ->
            binding.srContent.isRefreshing = false
            binding.loading.loading.visibility = View.GONE
            if (bagDataResponse.status) {
                bagAdapter?.setData(bagDataResponse.bagResponseData.cartItems)
                Timber.d("BagProductsSize:: ${bagDataResponse.bagResponseData.cartItems.size}")
                binding.tvTotalamount.text = String.format("${bagDataResponse.bagResponseData.total}$")
                sharedPref?.setDouble(getString(R.string.total_amount), bagDataResponse.bagResponseData.total)
                bagAdapter?.notifyDataSetChanged()

                binding.etPromocode.isEnabled = true
                binding.vNotHasProducts.visibility = View.GONE
                binding.content.rvBag.visibility = View.VISIBLE
            } else {
                binding.vNotHasProducts.visibility = View.VISIBLE
                binding.etPromocode.isEnabled = false
                binding.vNotHasProducts.isFocusable = false
                binding.content.rvBag.visibility = View.GONE
            }
        })

        viewModel.quantityBagUpdateMutableData.observe(viewLifecycleOwner, { bagUpdateResponse ->
            Timber.d("BagUpdatedResponse:: ${bagUpdateResponse.data.cart.quantity}")
            binding.tvTotalamount.text = String.format("${bagUpdateResponse.data.total}$")
            bagAdapter?.notifyDataSetChanged()
        })
    }

    private fun onClick() {
        bagAdapter?.onAddRemoveFavoriteClick = { addBagItemFavoriteClick ->
            binding.srContent.isRefreshing = true
            viewModel.addRemoveFavorite(addBagItemFavoriteClick.product.id).observe(viewLifecycleOwner, { response ->
                binding.srContent.isRefreshing = false
                DynamicToast.makeSuccess(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                viewModel.getBag()
            })
            bagAdapter?.notifyDataSetChanged()
        }

        bagAdapter?.onRemoveFromBagClick = { removeFromBagClick ->
            binding.srContent.isRefreshing = true
            viewModel.removeBagProduct(removeFromBagClick.product.id).observe(viewLifecycleOwner, { response ->
                binding.srContent.isRefreshing = false
                DynamicToast.makeSuccess(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                viewModel.getBag()
            })
            bagAdapter?.notifyDataSetChanged()
        }


        binding.btnCheckOut.setOnClickListener { v ->
            val getCodeText = binding.etPromocode.text.toString().trim()
            viewModel.checkPromoCode(getCodeText).observe(viewLifecycleOwner, { response ->
                if (response.status) {
                    sharedPref?.setInt(getString(R.string.promocode_id), response.data.id)
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
        bagAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.d("BagFragmentBinding::${_binding}")
    }
}