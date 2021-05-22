package com.e.commerce.ui.fragments.auth.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e.commerce.R
import com.e.commerce.databinding.FragmentForgotPasswordBinding
import com.e.commerce.ui.main.MainActivity

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private var viewModel: ForgotPasswordViewModel = ForgotPasswordViewModel()

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        initToolbar()
        initLayout()
    }


    private fun initLayout() {

    }

    private fun initToolbar() {
        (activity as MainActivity).setSupportActionBar(binding.toolbar.tool)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.toolbar.tool.setNavigationIcon(R.drawable.ic_back_row)
        binding.toolbar.tool.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }
}