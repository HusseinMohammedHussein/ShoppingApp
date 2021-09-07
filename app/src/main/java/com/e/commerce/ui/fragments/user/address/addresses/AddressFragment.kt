package com.e.commerce.ui.fragments.user.address.addresses

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
import com.e.commerce.data.model.auth.AddressPojo.AddressDataPojo.AddressObjectPojo
import com.e.commerce.databinding.FragmentAddressBinding
import com.e.commerce.ui.fragments.user.address.addresses.AddressAdapter.AddressInterface
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import timber.log.Timber

class AddressFragment : Fragment() {
    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!
    private var viewModel: AddressViewModel = AddressViewModel()
    private var addressAdapter: AddressAdapter? = null
    private var sharedPref: SharedPref? = null
    private var isHasNewAddressAdded: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AddressViewModel::class.java)
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
        onCLickListener()
    }

    private fun onCLickListener() {
        binding.fabAddAddress.setOnClickListener {
            val addressBundle = Bundle()
            addressBundle.putBoolean(resources.getString(R.string.isEditClicked), false)
            addressBundle.putParcelable(resources.getString(R.string.edit_address), null)
            val direction = AddressFragmentDirections.actionAddressToAddAddress().actionId
            findNavController().navigate(direction, addressBundle)
        }
    }

    private fun initToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = getString(R.string.address_fragment)
    }

    private fun init() {
        addressAdapter = AddressAdapter(onDeleteAddress)
        sharedPref = SharedPref(requireContext())
        isHasNewAddressAdded = sharedPref!!.getBoolean(getString(R.string.isHasNewAddressAdded))
        Timber.d("isHasNewAddressAdded::$isHasNewAddressAdded")
        binding.srlAddress.isRefreshing = false
        binding.srlAddress.setOnRefreshListener {
            viewModel.getAddress()
            addressAdapter?.notifyDataSetChanged()
        }

        binding.rvAddress.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = addressAdapter
            visibility = View.GONE
        }
    }

    private val onDeleteAddress = object : AddressInterface {
        override fun onAddressItemClick(address: AddressObjectPojo) {
            viewModel.deleteAddress(address.id).observe(viewLifecycleOwner, { response ->
                DynamicToast.makeSuccess(requireContext(), response.message, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun request() {
        viewModel.getAddress()
    }

    private fun observerData() {
        viewModel.addressMutableLD.observe(viewLifecycleOwner, { response ->
            binding.srlAddress.isRefreshing = false
            addressAdapter?.setData(response.data.data)
            binding.rvAddress.visibility = View.VISIBLE
        })
    }

    private fun isHasNewAddressAdded() {
        binding.srlAddress.isRefreshing = true
        if (isHasNewAddressAdded) {
            Timber.d("if(isHasNewAddressAdded)::$isHasNewAddressAdded")
            observerData()
        }
    }

    override fun onResume() {
        super.onResume()
        isHasNewAddressAdded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.d("AddressBinding::$_binding")
    }
}