package com.e.commerce.ui.fragments.auth.bag

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
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.collections.set

@AndroidEntryPoint
class BagFragment : Fragment(), NewQuantity {
    private var _binding: FragmentBagBinding? = null
    private val binding get() = _binding!!
    private var viewModel: BagViewModel = BagViewModel()
    private var bagAdapter: BagAdapter = BagAdapter(this)
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

    private fun onClick() {
        bagAdapter.onAddRemoveFavoriteClick = { addBagItemFavoriteClick ->
            viewModel.addRemoveFavorite(addBagItemFavoriteClick.product.id)
            binding.srContent.isRefreshing = true
            bagAdapter.notifyDataSetChanged()
        }

        bagAdapter.onRemoveFromBagClick = { removeFromBagClick ->
            binding.srContent.isRefreshing = true
            viewModel.removeBagProduct(removeFromBagClick.product.id)
            bagAdapter.notifyDataSetChanged()
        }
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
        viewModel.bagMutableData.observe(viewLifecycleOwner, { bagDataResponse ->
            binding.srContent.isRefreshing = false
            binding.loading.loading.visibility = View.GONE
            binding.content.rvBag.visibility = View.VISIBLE
            bagAdapter.setData(bagDataResponse.bagResponseData.cartItems)
            binding.tvTotalamount.text = String.format("${bagDataResponse.bagResponseData.total}$")
            Timber.d("TotalOfProducts::${bagDataResponse.bagResponseData.total}")
            bagAdapter.notifyDataSetChanged()
        })

        viewModel.quantityBagUpdateMutableData.observe(viewLifecycleOwner, { bagUpdateResponse ->
            Timber.d("BagUpdatedResponse:: ${bagUpdateResponse.data.cart.quantity}")
            binding.tvTotalamount.text = String.format("${bagUpdateResponse.data.total}$")
            bagAdapter.notifyDataSetChanged()
        })

        viewModel.addRemoveFavoriteMutableData.observe(viewLifecycleOwner, { favoriteResponse ->
            binding.srContent.isRefreshing = false
            if (favoriteResponse.message.isNotEmpty()) {
                DynamicToast.makeSuccess(requireContext(), favoriteResponse.message, 5000).show()
                favoriteResponse.message = ""
            } else {
                DynamicToast.makeSuccess(requireContext(), null, 5000).cancel()
            }
            Timber.d("favoriteResponse::${favoriteResponse.message}")
        })


        viewModel.removeBagProductMutableData.observe(viewLifecycleOwner, { removeProductResponse ->
            binding.srContent.isRefreshing = false
            if (removeProductResponse.message.isNotEmpty()) {
                Timber.d("RemoveProResponse_NO_MSG::${removeProductResponse.message}")
                DynamicToast.makeSuccess(requireContext(), removeProductResponse.message, 5000).show()
                removeProductResponse.message = ""
            } else {
                Timber.d("RemoveProResponse_MSG::${removeProductResponse.message}")
                DynamicToast.makeSuccess(requireContext(), null, 5000).cancel()
            }
            bagAdapter.notifyDataSetChanged()
        })
    }

    override fun newQuantity(cartId: Int, quantity: Int) {
        hashMap["quantity"] = quantity
        viewModel.updateQuantityBag(cartId, hashMap)
        Timber.d("cartId::${cartId} && Quantity::${quantity}")
    }

    override fun onResume() {
        super.onResume()
        binding.srContent.isRefreshing = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.d("BagFragmentBinding::${_binding}")
    }
}