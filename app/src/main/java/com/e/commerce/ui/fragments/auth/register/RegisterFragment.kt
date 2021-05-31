package com.e.commerce.ui.fragments.auth.register

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
import com.e.commerce.data.model.auth.RegisterPojo.RequestRegisterPojo
import com.e.commerce.databinding.FragmentRegisterBinding
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.regex.Pattern

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private var viewModel: RegisterViewModel = RegisterViewModel()
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        initTextWatcher()
        initToolbar()
        initLayout()
        observerData()
    }

    private fun observerData() {
        viewModel.responseRegisterMutable.observe(viewLifecycleOwner, { response ->
            if (response.status) {
                sharedPref.setString(resources.getString(R.string.user_token), response.data.token)
                sharedPref.setBoolean(resources.getString(R.string.is_user), response.status)

                Timber.d("UserToken::${sharedPref.getString(resources.getString(R.string.user_token))}")
                Timber.d("Is_User::${sharedPref.getBoolean(resources.getString(R.string.is_user))}")
                findNavController().navigate(R.id.action_signup_to_profile)

            } else {
                Toast.makeText(this.context, response.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initTextWatcher() {
        binding.etEmail.addTextChangedListener(EditTextWatcher(binding.etEmail))
        binding.etName.addTextChangedListener(EditTextWatcher(binding.etName))
        binding.etPhone.addTextChangedListener(EditTextWatcher(binding.etPhone))
        binding.etPassword.addTextChangedListener(EditTextWatcher(binding.etPassword))
    }

    private fun initLayout() {
        sharedPref = SharedPref(requireContext())

        binding.tvHaveaccount.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_login)
        }

        binding.btnSignup.setOnClickListener {
            if (validateEmail() && validateUsername() && validatePhone() && validatePassword()) {
                requestRegister()
            }
        }
    }

    private fun initToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbar.tool)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.toolbar.tool.setNavigationIcon(R.drawable.ic_back_row)
        binding.toolbar.tool.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun requestRegister() {
        val username: String = binding.etName.text.toString().trim()
        val phone: String = binding.etPhone.text.toString().trim()
        val email: String = binding.etEmail.text.toString().trim()
        val password: String = binding.etPassword.text.toString().trim()
        val requestRegisterPojo = RequestRegisterPojo(
            username, phone, email, password
        )

        Timber.d("Username:: ${requestRegisterPojo.username} | Email:: ${requestRegisterPojo.email} | Phone:: ${requestRegisterPojo.phone} | Password:: ${requestRegisterPojo.password}")
        viewModel.requestRegister(requestRegisterPojo)
    }

    private fun validateUsername(): Boolean {
        when {
            binding.etName.text.toString().trim().isEmpty() -> {
                binding.tilName.isErrorEnabled = true
                binding.tilName.error = "Required"
                requestFocus(binding.etName)
                return false
            }

            binding.etName.text.toString().trim().length < 10 -> {
                binding.tilName.isErrorEnabled = true
                binding.tilName.error = "Username should more than 10 letter!"
                requestFocus(binding.etName)
                return false
            }

            binding.etName.text.toString().trim().length > 30 -> {
                binding.tilName.isErrorEnabled = true
                binding.tilName.error = "Username equal 30 letter!"
                requestFocus(binding.etName)
                return false
            }

            binding.etName.text.toString().trim().length == 10 -> {
                binding.tilName.isErrorEnabled = false
                return false
            }

            binding.etName.text.toString().trim().length == 30 -> {
                binding.tilName.isErrorEnabled = false
                return false
            }

            else -> binding.tilName.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        val email: String = binding.etEmail.text.toString().trim()
        val isEmailValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        when {
            binding.etEmail.text.toString().trim().isEmpty() -> {
                binding.tilEmail.isErrorEnabled = true
                binding.tilEmail.error = "Required"
                requestFocus(binding.etEmail)
                return false
            }
            isEmailValid.not() -> {
                binding.tilEmail.isErrorEnabled = true
                binding.tilEmail.error = "Invalid Email address, ex: abc@example.com"
                requestFocus(binding.etEmail)
                return false
            }
            else -> binding.tilEmail.isErrorEnabled = false
        }
        return true
    }

    private fun validatePhone(): Boolean {
        when {
            binding.etPhone.text.toString().trim().length != 11 || !Pattern.matches(
                "(011|012|010|015)[0-9]{8}",
                binding.etPhone.text.toString()
            ) -> {
                binding.tilPhone.isErrorEnabled = true
                binding.tilPhone.error = "Phone number is Invalid!"
                requestFocus(binding.etPhone)
                return false
            }
            else -> binding.tilPhone.isErrorEnabled = false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        when {
            binding.etPassword.text.toString().trim().isEmpty() -> {
                binding.tilPassword.isErrorEnabled = true
                binding.tilPassword.error = "Required"
                requestFocus(binding.etPassword)
                return false
            }

            binding.etPassword.text.toString().trim().length < 6 -> {
                binding.tilPassword.isErrorEnabled = true
                binding.tilPassword.error = "Should be more than 6 letter!"
                requestFocus(binding.etPassword)
                return false
            }

            else -> binding.tilPassword.isErrorEnabled = false
        }
        return true
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    inner class EditTextWatcher constructor(val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            when (view.id) {
                R.id.et_email -> validateEmail()
                R.id.et_name -> validateUsername()
                R.id.et_phone -> validatePhone()
                R.id.et_password -> validatePassword()
            }
        }
    }
}