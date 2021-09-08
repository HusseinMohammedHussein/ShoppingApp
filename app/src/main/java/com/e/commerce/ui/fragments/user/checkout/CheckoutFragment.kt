package com.e.commerce.ui.fragments.user.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.e.commerce.R
import com.e.commerce.data.model.auth.address.AddressesDataPojo.AddressDataPojo
import com.e.commerce.data.model.auth.order.AddOrderPojo
import com.e.commerce.databinding.FragmentCheckoutBinding
import com.e.commerce.ui.component.ProfileViewModel
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import timber.log.Timber

class CheckoutFragment : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CheckoutViewModel
    private lateinit var profileVM: ProfileViewModel
    private var addressParcelable: AddressDataPojo? = null
    private lateinit var sharedPref: SharedPref

    private var getPaymentWay: Int = 0
    private var isUserHasPoints: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CheckoutViewModel::class.java)
        profileVM = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
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
        setArgumentsData()
        onClick()
        handleNoDataSelected()
    }

    private fun initToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = getString(R.string.checkout_fragment)
    }

    private fun init() {
        sharedPref = SharedPref(requireContext())
        Timber.d("getAddressClicked::${sharedPref}")
        addressParcelable = sharedPref.getAddressGson(getString(R.string.address_parcelable))
        Timber.d("getAddressClicked::${addressParcelable}")
    }


    private fun request() {
        profileVM.getProfile()
    }

    private fun observerData() {
        profileVM.profileMutableLD.observe(viewLifecycleOwner, { response ->
            isUserHasPoints = response.profileData.points != 0
            Timber.d("isUserHasPoints::$isUserHasPoints, UserPointsIs::${response.profileData.points}")
        })
    }

    private fun setArgumentsData() {
        if (addressParcelable != null) {
            addressParcelable?.let {
                binding.tvAddAddress.visibility = View.GONE
                binding.tvChangeAddress.visibility = View.VISIBLE
                binding.tvFullnameAddress.text = it.name
                binding.tvAddressDetails.text =
                    String.format("${it.details}, ${it.region}, ${it.city}")
            }
        } else {
            binding.tvAddAddress.visibility = View.VISIBLE
            binding.tvChangeAddress.visibility = View.GONE
            binding.tvFullnameAddress.visibility = View.GONE
            binding.tvAddressDetails.visibility = View.GONE
        }

        binding.tvTotalamount.text = String.format("${sharedPref.getDouble(getString(R.string.total_amount))}$")
        Timber.d("TotalAmount::${sharedPref.getDouble(getString(R.string.total_amount))}")
    }

    private fun onClick() {
        val direction = CheckoutFragmentDirections.actionCheckoutToAddress(null)

        binding.tvChangeAddress.setOnClickListener {
            findNavController().navigate(direction)
        }

        binding.cvCashPayment.setOnClickListener {
            binding.imgCashPaymentDisactive.visibility = View.GONE
            binding.imgOnlinePaymentDisactive.visibility = View.VISIBLE
            getPaymentWay = 1
        }

        binding.cvOnlinePayment.setOnClickListener {
            binding.imgCashPaymentDisactive.visibility = View.VISIBLE
            binding.imgOnlinePaymentDisactive.visibility = View.GONE
            getPaymentWay = 2
        }

        binding.tvAddAddress.setOnClickListener {
            findNavController().navigate(direction)
        }

        binding.btnSubmitOrder.setOnClickListener {
            val getPromoCodeId = sharedPref.getInt(getString(R.string.promocode_id))
            Timber.d("isUserHasPoints::$isUserHasPoints")
            val orderPojo = AddOrderPojo(addressParcelable?.id!!, getPaymentWay, isUserHasPoints, getPromoCodeId)
            viewModel.addOrder(orderPojo).observe(viewLifecycleOwner, { response ->
                if (response.status) {
                    DynamicToast.makeSuccess(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                } else {
                    DynamicToast.makeError(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun handleNoDataSelected() {
        if (binding.tvFullnameAddress.text.isNotEmpty() && binding.tvAddressDetails.text.isNotEmpty()) {
            binding.btnSubmitOrder.isEnabled = true
        } else binding.btnSubmitOrder.isEnabled =
            binding.imgCashPaymentDisactive.visibility == View.VISIBLE || binding.imgOnlinePaymentDisactive.visibility == View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}