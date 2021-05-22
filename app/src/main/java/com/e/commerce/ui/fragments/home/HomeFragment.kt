package com.e.commerce.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.databinding.FragmentHomeBinding
import com.e.commerce.ui.common.ProductsAdapter
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var viewModel: HomeViewModel = HomeViewModel()

    private var productAdapter: ProductsAdapter = ProductsAdapter()
    private var categoriesAdapter: CategoriesAdapter = CategoriesAdapter()

    private lateinit var sliderAdapter: BannerSliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        viewModel.getHomeLiveData()
        viewModel.getCategoriesLiveData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        init()
        listenerHandle()
        observeCategoryData()
    }

    private fun listenerHandle() {
        productAdapter.onItemClick = { productPojo ->
            viewModel.addFavorite(productPojo.id)
        }
    }


    private fun observeCategoryData() {
        viewModel.categoryPojoMutable.observe(viewLifecycleOwner, { categories ->
            categoriesAdapter.setData(categories.data.categories)
            categoriesAdapter.notifyDataSetChanged()

            viewModel.homePojoMutable.observe(viewLifecycleOwner, { home ->
                productAdapter.setData(home.homeData.products)
                Timber.d("observeProducts")
                productAdapter.notifyDataSetChanged()

                sliderAdapter.renewItem(home.homeData.banners)
                binding.bannerSlider.imageSlider.setSliderAdapter(sliderAdapter)

                binding.loading.loading.visibility = View.GONE
                binding.viewGroup.visibility = View.VISIBLE
            })
        })

        viewModel.favoritePojoMutable.observe(viewLifecycleOwner, { response ->
            if (response.status) {
                view?.let { Snackbar.make(it, response.message, Snackbar.LENGTH_LONG).show() }
                Timber.d("productFavoriteId::${response.data.id}")
            }
        })
    }


    private fun init() {
        binding.loading.loading.visibility = View.VISIBLE
        sliderAdapter = BannerSliderAdapter(requireContext())
        setupBannerSlider()
        binding.rvProduct.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
        }

        binding.rvCategories.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun setupBannerSlider() {
        binding.bannerSlider.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.bannerSlider.imageSlider.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION)
//       Best_Animation ->  CUBEINDEPTHTRANSFORMATION, CUBEINROTATIONTRANSFORMATION, FADETRANSFORMATION, CUBEOUTROTATIONTRANSFORMATION, DEPTHTRANSFORMATION
        binding.bannerSlider.imageSlider.autoCycleDirection =
            SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
//        binding.bannerSlider.imageSlider.indicatorSelectedColor = R.color.primary
//        binding.bannerSlider.imageSlider.indicatorUnselectedColor = R.color.white
        binding.bannerSlider.imageSlider.scrollTimeInSec = 4 //set scroll delay in seconds :
        binding.bannerSlider.imageSlider.startAutoCycle()
    }

    override fun onDestroy() {
        super.onDestroy()
        sliderAdapter.clearItem()
    }
}