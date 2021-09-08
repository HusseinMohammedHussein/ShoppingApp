package com.e.commerce.ui.fragments.user.register

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
import com.e.commerce.data.model.auth.register.RegisterDataPojo
import com.e.commerce.databinding.FragmentRegisterBinding
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import com.google.firebase.messaging.FirebaseMessaging
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import timber.log.Timber
import java.util.regex.Pattern

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        initTextWatcher()
        initToolbar()
        initLayout()
    }

    private fun initTextWatcher() {
        binding.etNameRegister.addTextChangedListener(EditTextWatcher(binding.etNameRegister))
        binding.etPhoneRegister.addTextChangedListener(EditTextWatcher(binding.etPhoneRegister))
        binding.etEmailRegister.addTextChangedListener(EditTextWatcher(binding.etEmailRegister))
        binding.etPasswordRegister.addTextChangedListener(EditTextWatcher(binding.etPasswordRegister))
    }

    private fun initLayout() {
        sharedPref = SharedPref(requireContext())

        binding.tvHaveaccount.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_login)
        }

        binding.btnSignup.setOnClickListener {
            if (validateUsername() && validatePhone() && validateEmail() && validatePassword()) {
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
        val username: String = binding.etNameRegister.text.toString().trim()
        val phone: String = binding.etPhoneRegister.text.toString().trim()
        val email: String = binding.etEmailRegister.text.toString().trim()
        val password: String = binding.etPasswordRegister.text.toString().trim()
        val registerPojo = RegisterDataPojo(username, phone, email, password)

        Timber.d("Username:: ${registerPojo.username} | Email:: ${registerPojo.email} | Phone:: ${registerPojo.phone} | Password:: ${registerPojo.password}")
        viewModel.requestRegister(registerPojo).observe(viewLifecycleOwner, { response ->
            if (response.status) {
                sharedPref.let {
                    it.setString(resources.getString(R.string.user_token), response.registerData.token)
                    it.setBoolean(resources.getString(R.string.is_user), response.status)
                }
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Timber.e("FCM Token Error!::${task.result}")
                        return@addOnCompleteListener
                    } else {
                        val getFCMToken = task.result.toString()
                        Timber.d("FCMTokenResult::$getFCMToken")
                        viewModel.setFCMToken(getFCMToken).observe(viewLifecycleOwner, { response ->
                            if (response.status) {
                                sharedPref.let {
                                    it.setString(getString(R.string.user_fcm_token), response.data.token)
                                    Timber.d("FCMTokenResponseApi::${response.data.token}")
                                }
                            } else {
                                Timber.e("FCMResponseError::${response.message}")
                            }
                        })
                    }
                }

                Timber.d("UserToken::${sharedPref.getString(resources.getString(R.string.user_token))}")
                Timber.d("Is_User::${sharedPref.getBoolean(resources.getString(R.string.is_user))}")
                val direction = RegisterFragmentDirections.actionSignupToProfile()
                findNavController().navigate(direction)
            } else {
                DynamicToast.makeError(requireContext(), response.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validateUsername(): Boolean {
        when {
            binding.etNameRegister.text.toString().trim().isEmpty() -> {
                binding.tilNameRegister.isErrorEnabled = true
                binding.tilNameRegister.error = "Required"
                requestFocus(binding.etNameRegister)
                return false
            }

            binding.etNameRegister.text.toString().trim().length < 10 -> {
                binding.tilNameRegister.isErrorEnabled = true
                binding.tilNameRegister.error = "Username should more than 10 letter!"
                requestFocus(binding.etNameRegister)
                return false
            }

            binding.etNameRegister.text.toString().trim().length > 30 -> {
                binding.tilNameRegister.isErrorEnabled = true
                binding.tilNameRegister.error = "Username equal 30 letter!"
                requestFocus(binding.etNameRegister)
                return false
            }

            binding.etNameRegister.text.toString().trim().length == 10 -> {
                binding.tilNameRegister.isErrorEnabled = false
                return false
            }

            binding.etNameRegister.text.toString().trim().length == 30 -> {
                binding.tilNameRegister.isErrorEnabled = false
                return false
            }

            else -> binding.tilNameRegister.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        val email: String = binding.etEmailRegister.text.toString().trim()
        val isEmailValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        when {
            binding.etEmailRegister.text.toString().trim().isEmpty() -> {
                binding.tilEmailRegister.isErrorEnabled = true
                binding.tilEmailRegister.error = "Required"
                requestFocus(binding.etEmailRegister)
                return false
            }
            isEmailValid.not() -> {
                binding.tilEmailRegister.isErrorEnabled = true
                binding.tilEmailRegister.error = "Invalid Email address, ex: abc@example.com"
                requestFocus(binding.etEmailRegister)
                return false
            }
            else -> binding.tilEmailRegister.isErrorEnabled = false
        }
        return true
    }

    private fun validatePhone(): Boolean {
        when {
            binding.etPhoneRegister.text.toString().trim().length != 11 || !Pattern.matches(
                "(011|012|010|015)[0-9]{8}",
                binding.etPhoneRegister.text.toString()
            ) -> {
                binding.tilPhoneRegister.isErrorEnabled = true
                binding.tilPhoneRegister.error = "Phone number is Invalid!"
                requestFocus(binding.etPhoneRegister)
                return false
            }
            else -> binding.tilPhoneRegister.isErrorEnabled = false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        when {
            binding.etPasswordRegister.text.toString().trim().isEmpty() -> {
                binding.tilPasswordRegister.isErrorEnabled = true
                binding.tilPasswordRegister.error = "Required"
                requestFocus(binding.etPasswordRegister)
                return false
            }

            binding.etPasswordRegister.text.toString().trim().length < 6 -> {
                binding.tilPasswordRegister.isErrorEnabled = true
                binding.tilPasswordRegister.error = "Should be more than 6 letter!"
                requestFocus(binding.etPasswordRegister)
                return false
            }

            else -> binding.tilPasswordRegister.isErrorEnabled = false
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
                R.id.et_name_register -> validateUsername()
                R.id.et_phone_register -> validatePhone()
                R.id.et_email_register -> validateEmail()
                R.id.et_password_register -> validatePassword()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.i("RegisterBindingIs::$_binding")
    }

    override fun onResume() {
        super.onResume()
        binding.tilNameRegister.isErrorEnabled = false
        binding.tilEmailRegister.isErrorEnabled = false
        binding.tilPhoneRegister.isErrorEnabled = false
        binding.tilPasswordRegister.isErrorEnabled = false
    }
}