package com.e.commerce.ui.fragments.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.e.commerce.R
import com.e.commerce.databinding.FragmentLoginBinding
import com.e.commerce.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var viewModel: LoginViewModel = LoginViewModel()

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
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
        binding.tvForgotPassword.setOnClickListener { forgetpassword ->
            forgetpassword.findNavController().navigate(R.id.action_login_to_forgot_password)
        }
        binding.btnLogin.setOnClickListener { login ->
            login.findNavController().navigate(R.id.action_login_to_signup)
        }
    }

    private fun initToolbar() {
        (activity as MainActivity).setSupportActionBar(binding.toolbar.tool)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.toolbar.tool.setNavigationIcon(R.drawable.ic_back_row)
        binding.toolbar.tool.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }
}