package com.e.commerce.ui.fragments.user.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.e.commerce.R
import com.e.commerce.data.model.auth.LoginPojo.LoginDataPojo
import com.e.commerce.databinding.FragmentLoginBinding
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var viewModel: LoginViewModel = LoginViewModel()
    private var sharedPref: SharedPref? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        methods()
    }

    private fun methods() {
        initToolbar()
        initLayout()
        initClasses()
        initTextWatcher()
    }

    private fun initClasses() {
        sharedPref = SharedPref(requireContext())
    }

    private fun initToolbar() {
        (activity as MainActivity).setSupportActionBar(binding.toolbar.tool)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.toolbar.tool.setNavigationIcon(R.drawable.ic_back_row)
        binding.toolbar.tool.setNavigationOnClickListener { findNavController().navigate(R.id.action_login_to_signup) }
    }

    private fun initLayout() {
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_forgot_password)
        }
        binding.btnLogin.setOnClickListener {
            if (validateEmail() && validatePassword()) {
                loginData()
            }
        }
    }

    private fun loginData() {
        val getEmail = binding.etEmailLogin.text.toString().trim()
        val getPassword = binding.etPasswordLogin.text.toString().trim()
        val loginData = LoginDataPojo(getEmail, getPassword)
        viewModel.login(loginData).observe(viewLifecycleOwner, { response ->
            if (response.status) {
                Timber.d("ResponseMessage::${response.data.token}")
                Timber.d("ResponseStatus::${response.status}")
                sharedPref?.setString(resources.getString(R.string.user_token), response.data.token)
                sharedPref?.setBoolean(resources.getString(R.string.is_user), response.status)
                DynamicToast.makeSuccess(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_login_to_profile) // TODO:: TestLogin_____
            } else {
                DynamicToast.makeError(requireContext(), response.message, Toast.LENGTH_SHORT).show()
            }
            Timber.d("UserToken::${sharedPref?.getString(resources.getString(R.string.user_token))}")
            Timber.d("Is_User::${sharedPref?.getBoolean(resources.getString(R.string.is_user))}")
            Timber.d("ResponseLoginMessage::${response.message}")
        })
    }


    private fun initTextWatcher() {
        binding.etEmailLogin.addTextChangedListener(EditTextWatcher(binding.etEmailLogin))
        binding.etPasswordLogin.addTextChangedListener(EditTextWatcher(binding.etPasswordLogin))
    }

    private fun validateEmail(): Boolean {
        val email: String = binding.etEmailLogin.text.toString().trim()
        val isEmailValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        when {
            binding.etEmailLogin.text.toString().trim().isEmpty() -> {
                binding.tilEmailLogin.isErrorEnabled = true
                binding.tilEmailLogin.error = "Required"
                requestFocus(binding.etEmailLogin)
                return false
            }
            isEmailValid.not() -> {
                binding.tilEmailLogin.isErrorEnabled = true
                binding.tilEmailLogin.error = "Invalid Email address, ex: abc@example.com"
                requestFocus(binding.etEmailLogin)
                return false
            }
            else -> binding.tilEmailLogin.isErrorEnabled = false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        when {
            binding.etPasswordLogin.text.toString().trim().isEmpty() -> {
                binding.tilPasswordLogin.isErrorEnabled = true
                binding.tilPasswordLogin.error = "Required"
                requestFocus(binding.etPasswordLogin)
                return false
            }

            binding.etPasswordLogin.text.toString().trim().length < 6 -> {
                binding.tilPasswordLogin.isErrorEnabled = true
                binding.tilPasswordLogin.error = "Should be more than 6 letter!"
                requestFocus(binding.etPasswordLogin)
                return false
            }

            else -> binding.tilPasswordLogin.isErrorEnabled = false
        }
        return true
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    inner class EditTextWatcher constructor(val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            when (view.id) {
                R.id.et_email_login -> validateEmail()
                R.id.et_password_login -> validatePassword()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.i("LoginBindingIs::$_binding")
    }
}