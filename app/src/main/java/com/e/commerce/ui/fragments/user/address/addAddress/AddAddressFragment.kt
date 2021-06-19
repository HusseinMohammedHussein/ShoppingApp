package com.e.commerce.ui.fragments.user.address.addAddress

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e.commerce.R
import com.e.commerce.data.model.auth.AddressPojo.AddAddressPojo
import com.e.commerce.data.model.auth.AddressPojo.AddressDataPojo.AddressObjectPojo
import com.e.commerce.databinding.FragmentAddAddressBinding
import com.e.commerce.ui.main.MainActivity
import com.hbb20.CountryCodePicker
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import timber.log.Timber

class AddAddressFragment : Fragment() {
    private var _binding: FragmentAddAddressBinding? = null
    private val binding get() = _binding!!
    private var viewModel: AddAddressViewModel = AddAddressViewModel()
    private var ccp: CountryCodePicker? = null
    private var getAddressDataPojo: AddressObjectPojo? = null
    private var isEditClick: Boolean? = false
    private var addressBundle: Bundle? = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AddAddressViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        initToolbar()
        init()
        initTextWatcher()
        onCLickListener()
    }

    private fun initTextWatcher() {
        binding.etFullnameAddaddress.addTextChangedListener(EditTextWatcher(binding.etFullnameAddaddress))
        binding.etAddressDetailsAddaddress.addTextChangedListener(EditTextWatcher(binding.etAddressDetailsAddaddress))
        binding.etCityAddaddress.addTextChangedListener(EditTextWatcher(binding.etCityAddaddress))
        binding.etRegionAddaddress.addTextChangedListener(EditTextWatcher(binding.etRegionAddaddress))
    }

    private fun initToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { (requireActivity() as MainActivity).onBackPressed() }
        binding.appbar.tvTitle.text = "Add Addresses"
    }

    private fun init() {
        ccp = CountryCodePicker(requireContext())
        addressBundle = arguments
        Timber.d(addressBundle?.getParcelable<AddressObjectPojo>(resources.getString(R.string.edit_address)).toString())
        getAddressDataPojo = addressBundle?.getParcelable(resources.getString(R.string.edit_address))
        isEditClick = addressBundle?.getBoolean("isEditClicked")
    }

    private fun onCLickListener() {
        binding.flChooseCountry.setOnClickListener {
            ccp?.launchCountrySelectionDialog()
            ccp?.setOnCountryChangeListener {
                binding.tvChooseCountryAddaddress.text = ccp?.selectedCountryEnglishName
                Timber.d("Selected Country Name::${ccp?.selectedCountryEnglishName}")
            }
        }
        /*        binding.btnSubmitAddAddress.setOnClickListener {
        if (fullNameValidate() &&
            addressDetailsValidate() &&
            cityValidate() &&
            regionValidate() &&
            chooseCountryValidate()
        ) {
            addNewAddressMode()
        } else {
            Timber.d("${fullNameValidate() && addressDetailsValidate() && regionValidate() && chooseCountryValidate()}")
        }
    }*/

        Timber.d("isEditClicked::$isEditClick")
        if (isEditClick == true) {
            getAddressDataPojo?.let {
                binding.btnSubmitEditAddress.setOnClickListener(editAddressSubmit)
                binding.btnSubmitEditAddress.visibility = View.VISIBLE
                binding.btnSubmitAddAddress.visibility = View.GONE
                binding.llNote.visibility = View.VISIBLE
                binding.llCountry.visibility = View.GONE
                editAddressMode(it)
            }
        } else {
            binding.llCountry.visibility = View.VISIBLE
            binding.llNote.visibility = View.GONE
            binding.btnSubmitEditAddress.visibility = View.GONE
            binding.btnSubmitAddAddress.visibility = View.VISIBLE
            binding.btnSubmitAddAddress.setOnClickListener(addAddressSubmit)
        }
    }

    private val editAddressSubmit = View.OnClickListener {
        if (fullNameValidate() &&
            addressDetailsValidate() &&
            cityValidate() &&
            regionValidate() &&
            chooseCountryValidate()
        ) {
            mEditAddressSubmit()
        } else {
            Timber.e("${fullNameValidate() && addressDetailsValidate() && regionValidate() && chooseCountryValidate()}")
        }
    }

    private val addAddressSubmit = View.OnClickListener {
        if (fullNameValidate() &&
            addressDetailsValidate() &&
            cityValidate() &&
            regionValidate() &&
            chooseCountryValidate()
        ) {
            addNewAddressMode()
        } else {
            Timber.e("${fullNameValidate() && addressDetailsValidate() && regionValidate() && chooseCountryValidate()}")
        }
    }

    private fun addNewAddressMode() {
        val getFullname = binding.etFullnameAddaddress.text.toString().trim()
        val getAddressDetails = binding.etAddressDetailsAddaddress.text.toString().trim()
        val getCity = binding.etCityAddaddress.text.toString().trim()
        val getRegion = binding.etRegionAddaddress.text.toString().trim()
        val getNotes = binding.etNotesAddaddress.text.toString().trim()

        val addAddress = AddAddressPojo(
            getFullname, getCity, getRegion, getAddressDetails, getNotes, 0, 0
        )

        viewModel.addAddress(addAddress).observe(viewLifecycleOwner, { response ->
            if (response.status) {
                requireActivity().onBackPressed()
            } else {
                DynamicToast.makeError(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                Timber.e("AddAddressError::${response.message}")
            }
        })
    }


    private fun editAddressMode(addressPojo: AddressObjectPojo) {
        binding.etFullnameAddaddress.setText(addressPojo.name)
        binding.etAddressDetailsAddaddress.setText(addressPojo.details)
        binding.etCityAddaddress.setText(addressPojo.city)
        binding.etRegionAddaddress.setText(addressPojo.region)
        binding.etNotesAddaddress.setText(if (addressPojo.notes.isEmpty()) "Add First Note" else addressPojo.notes)
    }

    private fun mEditAddressSubmit() {
        val getAddressId = getAddressDataPojo?.id
        val getFullname = binding.etFullnameAddaddress.text.toString().trim()
        val getAddressDetails = binding.etAddressDetailsAddaddress.text.toString().trim()
        val getCity = binding.etCityAddaddress.text.toString().trim()
        val getRegion = binding.etRegionAddaddress.text.toString().trim()
        val getNotes = binding.etNotesAddaddress.text.toString().trim()

        val editAddressPojo =
            getAddressId?.let {
                AddressObjectPojo(
                    it, getFullname, getCity, getNotes, getRegion, getAddressDetails, 0.0, 0.0
                )
            }
        Timber.i("$getAddressId, $getFullname, $getCity, $getRegion, $getAddressDetails")

        if (getAddressId != null && editAddressPojo != null) {
            viewModel.updateAddress(getAddressId, editAddressPojo).observe(viewLifecycleOwner, { response ->
                if (response.status) {
                    requireActivity().onBackPressed()
                } else {
                    DynamicToast.makeError(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun fullNameValidate(): Boolean {
        when {
            binding.etFullnameAddaddress.text.toString().trim().isEmpty() -> {
                binding.tilFullnameTitleAddaddress.isErrorEnabled = true
                binding.tilFullnameTitleAddaddress.error = "Required!"
                requestFocus(binding.etFullnameAddaddress)
                return false
            }
            else -> binding.tilFullnameTitleAddaddress.isErrorEnabled = false
        }
        return true
    }

    private fun addressDetailsValidate(): Boolean {
        when {
            binding.etAddressDetailsAddaddress.text.toString().trim().isEmpty() -> {
                binding.tilAddressDetailsTitleAddaddress.isErrorEnabled = true
                binding.tilAddressDetailsTitleAddaddress.error = "Required!"
                requestFocus(binding.etAddressDetailsAddaddress)
                return false
            }

            else -> binding.tilAddressDetailsTitleAddaddress.isErrorEnabled = false
        }
        return true
    }

    private fun cityValidate(): Boolean {
        when {
            binding.etCityAddaddress.text.toString().trim().isEmpty() -> {
                binding.tilCityTitleAddaddress.isErrorEnabled = true
                binding.tilCityTitleAddaddress.error = "Required!"
                requestFocus(binding.etCityAddaddress)
                return false
            }
            else -> binding.tilCityTitleAddaddress.isErrorEnabled = false
        }
        return true
    }

    private fun regionValidate(): Boolean {
        when {
            binding.etRegionAddaddress.text.toString().trim().isEmpty() -> {
                binding.tilRegionTitleAddaddress.isErrorEnabled = true
                binding.tilRegionTitleAddaddress.error = "Required!"
                requestFocus(binding.etRegionAddaddress)
                return false
            }
            else -> binding.tilRegionTitleAddaddress.isErrorEnabled = false
        }
        return true
    }

    private fun chooseCountryValidate(): Boolean {
        when {
            binding.tvChooseCountryAddaddress.text.toString().trim().isEmpty() -> {
                binding.tvChooseCountryAddaddress.hint = "Choose Country Required!"
                binding.tvChooseCountryAddaddress.setHintTextColor(Color.RED)
                return false
            }
            else -> binding.tvChooseCountryAddaddress.setTextColor(Color.BLACK)
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
                R.id.et_fullname_addaddress -> fullNameValidate()
                R.id.et_address_details_addaddress -> addressDetailsValidate()
                R.id.et_city_addaddress -> cityValidate()
                R.id.et_region_addaddress -> regionValidate()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.d("AddAddressBinding::$_binding")
    }
}