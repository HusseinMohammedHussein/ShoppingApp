package com.e.commerce.ui.fragments.auth.settingprofile

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e.commerce.R
import com.e.commerce.data.model.auth.SettingProfilePojo
import com.e.commerce.databinding.FragmentSettingProfileBinding
import com.e.commerce.ui.main.MainActivity
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.model.Image
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import java.util.regex.Pattern

@AndroidEntryPoint
class SettingProfileFragment : Fragment() {
    private var _binding: FragmentSettingProfileBinding? = null
    private val binding get() = _binding!!

    private var viewModel: SettingViewModel = SettingViewModel()
    private lateinit var pathImage: Image

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingProfileBinding.inflate(inflater, container, false)
//        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SettingViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        initTextWatcher()
        initToolbar()
        chooseImage()
        request()
        response()
    }

    private fun response() {
        viewModel.settingProfileMutableData.observe(viewLifecycleOwner, { updateResponse ->
            if (updateResponse.status) {
                Timber.d("UpdateStatus::${updateResponse.status}")
                (requireActivity() as MainActivity).onBackPressed()
            }
        })
    }

    private fun initTextWatcher() {
        binding.etEmailSetting.addTextChangedListener(EditTextWatcher(binding.etEmailSetting))
        binding.etFullnameSetting.addTextChangedListener(EditTextWatcher(binding.etFullnameSetting))
        binding.etPhoneSetting.addTextChangedListener(EditTextWatcher(binding.etPhoneSetting))
    }

    private fun initToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { (requireActivity() as MainActivity).onBackPressed() }
        binding.appbar.tvTitle.text = ""
    }

    private fun request() {
        binding.btnSubmitSetting.setOnClickListener {
            Timber.d("ButtonOnClick")
            if (validatePhoneSetting() && validateEmailSetting() && validateFullnameSetting()) {
                Timber.d("ButtonOnClick::AfterValidation")
                update()
            }
        }
    }

    private fun update() {
        val fullname: String = binding.etFullnameSetting.text.toString().trim()
        val phone: String = binding.etPhoneSetting.text.toString().trim()
        val email: String = binding.etEmailSetting.text.toString().trim()
        val settingProfilePojo = SettingProfilePojo(
            fullname, email, phone, getImageRequestBody(pathImage.path, "image")!!
        )
        Timber.d("Fullname:: $fullname | Email:: $email ")
        viewModel.setSettingProfile(settingProfilePojo)
    }

    private fun chooseImage() {
        binding.icChangeImage.setOnClickListener {
            Dexter.withContext(requireContext())
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report?.areAllPermissionsGranted()!!) {
                            ImagePicker.create(this@SettingProfileFragment)
                                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                                .toolbarFolderTitle("Folder") // folder selection title
                                .toolbarImageTitle("Tap to select") // image selection title
                                .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                                .single() // single mode
                                .limit(1) // max images can be selected (99 by default)
                                .showCamera(true) // show camera or not (true by default)
                                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                                .theme(R.style.ef_BaseTheme) // must inherit ef_BaseTheme. please refer to sample
                                .enableLog(true) // disabling log
                                .start() // start image picker activity with request code
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }
                }).check()
        }
    }

    private fun getImageRequestBody(imagePath: String?, name: String?): MultipartBody.Part? {
        var part: MultipartBody.Part? = null
        if (imagePath != null) {
            if ((imagePath == "null").not()) {
                val file = File(imagePath)
                val request_body = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                part = name?.let { MultipartBody.Part.createFormData(it, file.name, request_body) }
            }
        }
        return part
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)
            if (image != null) {
                val file = File(image.path)
                if (file.exists()) {
                    Picasso.get().load(file).into(binding.imgPerson)
                    pathImage = image
                }
            }
        }
    }

    private fun validateFullnameSetting(): Boolean {
        when {
            binding.etFullnameSetting.text.toString().trim().isEmpty() -> {
                binding.tilFullnameSetting.isErrorEnabled = true
                binding.tilFullnameSetting.error = "Required!"
                requestFocus(binding.etFullnameSetting)
                return false
            }

            binding.etFullnameSetting.text.toString().trim().length > 30 -> {
                binding.tilFullnameSetting.isErrorEnabled = true
                binding.tilFullnameSetting.error = "It should be 30 characters"
                requestFocus(binding.etFullnameSetting)
                return false
            }

            binding.etFullnameSetting.text.toString().trim().length <= 30 -> {
                binding.tilFullnameSetting.isErrorEnabled = false
                return false
            }
            else -> binding.tilFullnameSetting.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmailSetting(): Boolean {
        val email: String = binding.etEmailSetting.text.toString().trim()
        val isEmailValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        Timber.d("EmailIs:: $email | isEmailValid:: $isEmailValid")
        when {
            binding.etEmailSetting.text.toString().trim().isEmpty() -> {
                binding.tilEmailSetting.isErrorEnabled = true
                binding.tilEmailSetting.error = "Required!"
                requestFocus(binding.etEmailSetting)
                return false
            }

            isEmailValid.not() -> {
                binding.tilEmailSetting.isErrorEnabled = true
                binding.tilEmailSetting.error = "Invalid"
                requestFocus(binding.etEmailSetting)
                return false
            }

            else -> binding.tilEmailSetting.isErrorEnabled = false
        }
        return true
    }

    private fun validatePhoneSetting(): Boolean {
        when {
            binding.etPhoneSetting.text.toString().trim().isEmpty() -> {
                binding.tilPhoneSetting.isErrorEnabled = true
                binding.tilPhoneSetting.error = "Required!"
                requestFocus(binding.etPhoneSetting)
                return false
            }

            !Pattern.matches("(011|012|010|015)[0-9]{8}", binding.etPhoneSetting.text.toString()) ||
                    binding.etPhoneSetting.text.toString().trim().length != 11 -> {
                binding.tilPhoneSetting.isErrorEnabled = true
                binding.tilPhoneSetting.error = "Invalid Number!"
                requestFocus(binding.etPhoneSetting)
                return false
            }
            else -> binding.tilPhoneSetting.isErrorEnabled = false
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
                R.id.et_email_setting -> validateEmailSetting()
                R.id.et_fullname_setting -> validateFullnameSetting()
                R.id.et_phone_setting -> validatePhoneSetting()
            }
        }
    }
}