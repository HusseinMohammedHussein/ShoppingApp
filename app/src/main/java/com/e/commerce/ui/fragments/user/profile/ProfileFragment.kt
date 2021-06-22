package com.e.commerce.ui.fragments.user.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.e.commerce.R
import com.e.commerce.databinding.FragmentProfileBinding
import com.e.commerce.ui.common.ProfileViewModel
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var viewModel: ProfileViewModel = ProfileViewModel()

    private var sharedPref: SharedPref? = null
    private var isUser: Boolean? = false
    private var userToken: String? = null

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
            binding.content.tvUsername.text = response.data.name
            Timber.d("getUserName::${response.data.name}")
            binding.content.tvEmail.text = response.data.email
            sharedPref?.setInt(getString(R.string.user_points), response.data.points)
            Picasso.get()
                .load(response.data.image)
                .into(binding.content.imgProfile)
        })

        viewModel.totalOrdersMutableLD.observe(viewLifecycleOwner, { orderResponse ->
            Timber.d("TotalOrders::${orderResponse.data.total}")
            binding.content.contentOption.tvMyordersCount.text = String.format("Already have ${orderResponse.data.total} orders")
        })

        viewModel.addressMutableLD.observe(viewLifecycleOwner, { addressResponse ->
            if (addressResponse.status) {
                Timber.d("TotalOrders::${addressResponse.data.total}")
                binding.content.contentOption.tvShippingAddress.text = String.format("${addressResponse.data.total} address")
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
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }


    private fun onClick() {
        binding.content.contentOption.rlSetting.setOnClickListener {
            val direction = ProfileFragmentDirections.actionProfileToUpdateFragment()
            findNavController().navigate(direction)
        }

        binding.content.contentOption.llMyOrders.setOnClickListener {
            val direction = ProfileFragmentDirections.actionProfileToOrders()
            findNavController().navigate(direction)
        }

        binding.content.contentOption.rlShippingAddress.setOnClickListener {
            val directions = ProfileFragmentDirections.actionProfileToAddress(null)
            findNavController().navigate(directions)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Timber.w("ProfileBindingIs::$_binding")
    }

    override fun onResume() {
        super.onResume()
        _binding = binding
    }
}