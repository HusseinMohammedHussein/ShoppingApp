package com.e.commerce.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.data.model.ProductPojo
import com.e.commerce.databinding.FragmentHomeBinding
import com.e.commerce.ui.common.CustomAlertDialog
import com.e.commerce.ui.common.ProductsAdapter
import com.e.commerce.ui.common.ProductsAdapter.ProductOnClick
import com.e.commerce.util.SharedPref
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import timber.log.Timber

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var viewModel: HomeViewModel = HomeViewModel()
    private var sharedPref: SharedPref? = null

    private var productAdapter: ProductsAdapter? = null
    private var sliderAdapter: BannerSliderAdapter? = null
    private var categoriesAdapter: CategoriesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        init()
        request()
        observerData()
        setupBannerSlider()
    }

    private fun init() {
        sharedPref = SharedPref(requireContext())
        productAdapter = ProductsAdapter(onProductClick, requireContext())
        categoriesAdapter = CategoriesAdapter()
        sliderAdapter = BannerSliderAdapter(requireContext())
        binding.loading.loading.visibility = View.VISIBLE

        binding.rvProduct.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
            ViewCompat.setNestedScrollingEnabled(binding.rvProduct, false)
        }

        binding.rvCategories.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
            ViewCompat.setNestedScrollingEnabled(binding.rvCategories, false)
        }
    }

    private fun request() {
        viewModel.getHomeLiveData()
        viewModel.getCategoriesLiveData()
    }

    private fun observerData() {
        viewModel.categoryPojoMutable.observe(viewLifecycleOwner, { categories ->
            if (categories.status) {
                categoriesAdapter?.let { adapter ->
                    adapter.setData(categories.data.categories)
                    adapter.notifyDataSetChanged()
                }
            }

            viewModel.homePojoMutable.observe(viewLifecycleOwner, { home ->
                if (home.status) {
                    productAdapter?.let { adapter ->
                        adapter.setData(home.homeData.products)
                        adapter.notifyDataSetChanged()
                    }
                }

                if (home.homeData.banners.isNotEmpty()) {
                    sliderAdapter?.let { adapter ->
                        adapter.renewItem(home.homeData.banners)
                        binding.bannerSlider.imageSlider.setSliderAdapter(adapter)
                    }
                    Timber.w("SliderHasData:::${home.homeData.banners.size}")
                }
                binding.loading.loading.visibility = View.GONE
                binding.viewGroup.visibility = View.VISIBLE
            })
        })
    }

    private val onProductClick = object : ProductOnClick {
        override fun onProductItemClick(product: ProductPojo) {
            val getUserToken = sharedPref?.getBoolean(getString(R.string.is_user))!!
            if (!getUserToken) {
                val direction = HomeFragmentDirections.actionHomeToSignup()
                CustomAlertDialog.alertDialog(requireContext(), this@HomeFragment, direction)
            } else {
                viewModel.addOrRemoveFromFavorite(product.id).observe(viewLifecycleOwner, { response ->
                    Timber.d("productFavoriteMessage::${response.message}")
                    Timber.d("productFavoriteStatus::${response.status}")
                })
            }
        }
    }

    private fun setupBannerSlider() {
        binding.bannerSlider.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM) // set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.bannerSlider.imageSlider.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION)
        binding.bannerSlider.imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        binding.bannerSlider.imageSlider.scrollTimeInSec = 5 //set scroll delay in seconds:
        binding.bannerSlider.imageSlider.startAutoCycle()
        // Best_Animation ->  CUBEINDEPTHTRANSFORMATION, CUBEINROTATIONTRANSFORMATION, FADETRANSFORMATION, CUBEOUTROTATIONTRANSFORMATION, DEPTHTRANSFORMATION
        //  binding.bannerSlider.imageSlider.indicatorSelectedColor = R.color.primary
        //   binding.bannerSlider.imageSlider.indicatorUnselectedColor = R.color.white
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.bannerSlider.imageSlider.stopAutoCycle()
        sliderAdapter = null
        Timber.i("HomeSliderAdapter::$sliderAdapter")
        _binding = null
        Timber.i("HomeBinding::${_binding}")
    }
}