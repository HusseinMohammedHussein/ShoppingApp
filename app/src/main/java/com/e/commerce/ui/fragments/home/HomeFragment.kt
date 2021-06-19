package com.e.commerce.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.databinding.FragmentHomeBinding
import com.e.commerce.ui.common.ProductsAdapter
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var viewModel: HomeViewModel = HomeViewModel()

    private var productAdapter: ProductsAdapter = ProductsAdapter()
    private var categoriesAdapter: CategoriesAdapter = CategoriesAdapter()

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
//        retainInstance = true
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
        listenerHandle()
    }

    private fun request() {
        viewModel.getHomeLiveData()
        viewModel.getCategoriesLiveData()
    }

    private fun listenerHandle() {
        productAdapter.onItemClick = { productPojo ->
            viewModel.addOrRemoveFromFavorite(productPojo.id).observe(this.viewLifecycleOwner, { response ->
                if (response.status) {
                    DynamicToast.makeSuccess(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
                Timber.d("productFavoriteMessage::${response.message}")
                Timber.d("productFavoriteStatus::${response.status}")
            })
        }
    }


    private fun observerData() {
        viewModel.categoryPojoMutable.observe(viewLifecycleOwner, { categories ->
            categoriesAdapter.setData(categories.data.categories)
            categoriesAdapter.notifyDataSetChanged()

            viewModel.homePojoMutable.observe(viewLifecycleOwner, { home ->
                productAdapter.setData(home.homeData.products)
                productAdapter.notifyDataSetChanged()

                if (home.homeData.banners.isNotEmpty()) {
                    sliderAdapter?.let {
                        it.renewItem(home.homeData.banners)
                        binding.bannerSlider.imageSlider.setSliderAdapter(it)
                    }
                    Timber.w("SliderHasData:::${home.homeData.banners.size}")
                }
                binding.loading.loading.visibility = View.GONE
                binding.viewGroup.visibility = View.VISIBLE
            })
        })
    }


    private fun init() {
        binding.loading.loading.visibility = View.VISIBLE
        sliderAdapter = BannerSliderAdapter(requireContext())

        binding.rvProduct.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
        }

        binding.rvCategories.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }

        ViewCompat.setNestedScrollingEnabled(binding.rvProduct, false)
        ViewCompat.setNestedScrollingEnabled(binding.rvCategories, false)
    }

    private fun setupBannerSlider() {
        binding.bannerSlider.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.bannerSlider.imageSlider.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION)
//       Best_Animation ->  CUBEINDEPTHTRANSFORMATION, CUBEINROTATIONTRANSFORMATION, FADETRANSFORMATION, CUBEOUTROTATIONTRANSFORMATION, DEPTHTRANSFORMATION
        binding.bannerSlider.imageSlider.autoCycleDirection =
            SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
//        binding.bannerSlider.imageSlider.indicatorSelectedColor = R.color.primary
//        binding.bannerSlider.imageSlider.indicatorUnselectedColor = R.color.white
        binding.bannerSlider.imageSlider.scrollTimeInSec = 5 //set scroll delay in seconds :
        binding.bannerSlider.imageSlider.startAutoCycle()
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