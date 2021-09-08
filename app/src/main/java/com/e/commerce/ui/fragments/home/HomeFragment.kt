package com.e.commerce.ui.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.data.model.product.ProductPojo
import com.e.commerce.databinding.FragmentHomeBinding
import com.e.commerce.interfaces.ProductOnClick
import com.e.commerce.ui.component.CustomAlertDialog
import com.e.commerce.ui.component.ProductsAdapter
import com.e.commerce.util.SharedPref
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import timber.log.Timber

@SuppressLint("NotifyDataSetChanged")
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: SharedPref
    private lateinit var viewModel: HomeViewModel
    private lateinit var productAdapter: ProductsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    private var sliderAdapter: BannerSliderAdapter? = null

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
        binding.loading.loading.visibility = View.VISIBLE
        binding.rvProduct.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            ViewCompat.setNestedScrollingEnabled(binding.rvProduct, false)
        }

        binding.rvCategories.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
                categoriesAdapter = CategoriesAdapter(categories.categoriesData.categoriesData)
                binding.rvCategories.adapter = categoriesAdapter
            }

            viewModel.homePojoMutable.observe(viewLifecycleOwner, { home ->
                if (home.status) {
                    productAdapter = ProductsAdapter(requireContext(), onProductClick, home.homeData.products)
                    binding.rvProduct.adapter = productAdapter
                }

                if (home.homeData.banners.isNotEmpty()) {
                    sliderAdapter = BannerSliderAdapter(requireContext(), home.homeData.banners)
                    binding.bannerSlider.imageSlider.setSliderAdapter(sliderAdapter!!)
                    Timber.w("SliderHasData:::${home.homeData.banners.size}")
                }
                binding.loading.loading.visibility = View.GONE
                binding.viewGroup.visibility = View.VISIBLE
            })
        })
    }

    private val onProductClick = object : ProductOnClick {
        override fun onAddRemoveProductFavoriteClick(product: ProductPojo) {
            val getUserToken = sharedPref.getBoolean(getString(R.string.is_user))
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