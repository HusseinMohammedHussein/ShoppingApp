package com.e.commerce.ui.fragments.user.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.e.commerce.R
import com.e.commerce.data.model.auth.profile.ProfileDataPojo
import com.e.commerce.databinding.FragmentProfileBinding
import com.e.commerce.ui.component.ProfileViewModel
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import timber.log.Timber

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var viewModel: ProfileViewModel = ProfileViewModel()

    private var sharedPref: SharedPref? = null
    private var isUser: Boolean? = false
    private var userToken: String? = null
    private var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
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
        onClick()
    }

    private fun request() {
        viewModel.getProfile()
        viewModel.getTotalOrders()
        viewModel.getAddress()
    }

    private fun observerData() {
        viewModel.profileMutableLD.observe(viewLifecycleOwner, { response ->
            if (response.status) {
                bundle.putParcelable(getString(R.string.profilePojo), response.profileData)
                Timber.d("ProfilePojoBundle::${bundle.getParcelable<ProfileDataPojo>(getString(R.string.profilePojo))}")
                binding.content.tvUsername.text = response.profileData.name
                Timber.d("getUserName::${response.profileData.name}")
                binding.content.tvEmail.text = response.profileData.email
                sharedPref?.setInt(getString(R.string.user_points), response.profileData.points)
                Glide.with(requireContext())
                    .load(response.profileData.image)
                    .into(binding.content.imgProfile)
            } else {
                DynamicToast.makeError(requireContext(), response.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.totalOrdersMutableLD.observe(viewLifecycleOwner, { orderResponse ->
            Timber.d("TotalOrders::${orderResponse.orderData.total}")
            binding.content.contentOption.tvMyordersCount.text = String.format("Already have ${orderResponse.orderData.total} orders")
        })

        viewModel.addressMutableLD.observe(viewLifecycleOwner, { addressResponse ->
            if (addressResponse.status) {
                Timber.d("TotalOrders::${addressResponse.addressData.total}")
                binding.content.contentOption.tvShippingAddress.text = String.format("${addressResponse.addressData.total} address")
            }
        })
    }

    private fun init() {
        sharedPref = SharedPref(requireContext())
        isUser = sharedPref?.getBoolean(getString(R.string.is_user))
        userToken = sharedPref?.getString(resources.getString(R.string.user_token)).toString()
    }

    private fun initToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener {
            val direction = ProfileFragmentDirections.actionProfileToHome()
            findNavController().navigate(direction)
        }
    }

    private fun onClick() {
        binding.content.contentOption.rlSetting.setOnClickListener {
            val direction = ProfileFragmentDirections.actionProfileToSettingProfile().actionId
            findNavController().navigate(direction, bundle)
        }

        binding.content.contentOption.llMyOrders.setOnClickListener {
            val direction = ProfileFragmentDirections.actionProfileToOrders()
            findNavController().navigate(direction)
        }

        binding.content.contentOption.rlShippingAddress.setOnClickListener {
            val directions = ProfileFragmentDirections.actionProfileToAddress(null)
            findNavController().navigate(directions)
        }

        binding.content.btnLogout.setOnClickListener {
            val getFCMToken = sharedPref?.getString(getString(R.string.user_fcm_token))
            if (getFCMToken != null) {
                viewModel.logout(getFCMToken).observe(viewLifecycleOwner, { response ->
                    if (response.status) {
                        sharedPref?.let {
                            it.setString(getString(R.string.user_token), response.logoutData.token)
                            it.setBoolean(getString(R.string.is_user), false)
                            it.clear()
                        }

                        val direction = ProfileFragmentDirections.actionProfileToHome()
                        findNavController().navigate(direction)
                    } else {
                        DynamicToast.makeError(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                viewModel.logout("FCMTokenNULL")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        _binding = binding
    }
}