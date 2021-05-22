package com.e.commerce.ui.fragments.auth.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e.commerce.R
import com.e.commerce.databinding.FragmentProfileBinding
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var viewModel: ProfileViewModel = ProfileViewModel()
    private lateinit var sharedPref: SharedPref

    private var isUser: Boolean = false
    private lateinit var userToken: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
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
        observerData()
    }

    private fun observerData() {
        viewModel.getProfile()
        viewModel.profileMutable.observe(viewLifecycleOwner, { response ->
            binding.content.tvUsername.text = response.data.name
            Timber.d("getUserName::${response.data.name}")
            binding.content.tvEmail.text = response.data.email

            Picasso.get()
                .load(response.data.image)
                .into(binding.content.imgProfile)
//            binding.content.contentOption.text = response.data.email
        })
    }

    private fun init() {
        sharedPref = SharedPref(requireActivity())
        isUser = sharedPref.getBoolean(resources.getString(R.string.is_user))
        userToken = sharedPref.getString(resources.getString(R.string.user_token)).toString()
    }

    private fun initToolbar() {
        (activity as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { (requireActivity() as MainActivity).onBackPressed() }
        binding.appbar.tvTitle.text = ""
    }

}