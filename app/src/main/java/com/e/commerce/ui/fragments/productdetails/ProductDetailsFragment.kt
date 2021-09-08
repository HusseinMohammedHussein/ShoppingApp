package com.e.commerce.ui.fragments.productdetails

import android.annotation.SuppressLint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.e.commerce.R
import com.e.commerce.data.model.product.ProductDetailsPojo
import com.e.commerce.data.model.product.ProductPojo
import com.e.commerce.data.model.product.ProductsPojo
import com.e.commerce.databinding.FragmentProductDetailsBinding
import com.e.commerce.interfaces.ProductOnClick
import com.e.commerce.ui.component.CustomAlertDialog
import com.e.commerce.ui.component.ProductsAdapter
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import timber.log.Timber

@SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
class ProductDetailsFragment : Fragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var productPojoParcelable: ProductPojo
    private lateinit var sharedPref: SharedPref
    private lateinit var viewModel: ProductDetailsViewModel
    private lateinit var imagesAdapter: ProductImageAdapter
    private lateinit var productsAdapter: ProductsAdapter

    private var getUserToken: Boolean = false
    private var bundle: Bundle = Bundle()
    private var y = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductDetailsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        Timber.d("Binding::${binding}")
        init()
        setupToolbar()
        observeData()
        onClickListener()
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { (activity as MainActivity).onBackPressed() }
        binding.appbar.tvTitle.text = productPojoParcelable.name
    }

    private fun init() {
        bundle = requireArguments()
        sharedPref = SharedPref(requireContext())
        getUserToken = sharedPref.getBoolean(getString(R.string.is_user))
        Timber.d("getUserToken::$getUserToken")
        productPojoParcelable = bundle.getParcelable(resources.getString(R.string.product_pojo))!!
        setupViewPager()

        binding.content.rvProducts.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        binding.loading.loading.visibility = View.VISIBLE
        binding.content.loading.loading.visibility = View.VISIBLE
    }

    private fun observeData() {
        viewModel.getProductDetails(productPojoParcelable.id)
        Timber.d("productID::${productPojoParcelable.id}")
        viewModel.getProducts()

        viewModel.productDetailsLiveData.observe(viewLifecycleOwner, { productDetails ->
            binding.groupViews.visibility = View.VISIBLE
            binding.loading.loading.visibility = View.GONE
            productDetailsViews(productDetails)
        })

        viewModel.productsLiveData.observe(viewLifecycleOwner, { products ->
            binding.content.loading.loading.visibility = View.GONE
            productsList(products)
            //  Handler(Looper.getMainLooper()).postDelayed({}, 5000)
        })

//        viewModel.addToBagLiveData.observe(viewLifecycleOwner, { cartResponse ->
//            Toast.makeText(requireContext(), cartResponse.message, Toast.LENGTH_SHORT).show()
////            viewModel.getProductDetails(productPojoParcelable.id)
//        })
//
//        viewModel.addToFavoriteLiveData.observe(viewLifecycleOwner, { response ->
//            Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
//        })
    }

    private fun productsList(products: ProductsPojo) {
        productsAdapter = ProductsAdapter(requireContext(), onProductClick, products.productsData.products)
        binding.content.rvProducts.adapter = productsAdapter
        binding.content.rvProducts.visibility = View.VISIBLE
        binding.content.tvCountItems.text = String.format("${products.productsData.total} items")
    }

    private fun productDetailsViews(productDetails: ProductDetailsPojo) {
        binding.content.tvProName.text = productDetails.productDetailsData.name
        binding.content.tvProPrice.text = String.format("${productDetails.productDetailsData.price} $")
        binding.content.tvProOldprice.text =
            String.format("${productDetails.productDetailsData.old_price} $")
        binding.content.tvProOldprice.paintFlags = STRIKE_THRU_TEXT_FLAG
        binding.content.tvProDesc.text = productDetails.productDetailsData.description
        if (productDetails.productDetailsData.in_favorites) {
            binding.content.icFavorite.setImageResource(R.drawable.ic_favorite_active)
            y++
        }

        Timber.d("ThisInCart::${productDetails.productDetailsData.in_cart}")
        if (productDetails.productDetailsData.in_cart) {
            binding.content.btnAddCart.text = "Remove From Cart"
        } else {
            binding.content.btnAddCart.text = "Add To Cart"
        }

        imagesAdapter = ProductImageAdapter(productDetails.productDetailsData.images)
        binding.content.vpProImages.adapter = imagesAdapter
        binding.content.dotsIndicator.setViewPager2(binding.content.vpProImages)
    }

    private fun onClickListener() {
        val direction = ProductDetailsFragmentDirections.actionProductDetailsToSignup()
        Timber.d("getUserToken::$getUserToken")
        binding.content.btnAddCart.setOnClickListener {
            if (!getUserToken) {
                CustomAlertDialog.alertDialog(requireContext(), this@ProductDetailsFragment, direction)
            } else {
                if (binding.content.btnAddCart.text.equals("Add To Cart")) {
                    viewModel.addOrRemoveFromCart(productPojoParcelable.id)
                    binding.content.btnAddCart.text = "Remove From Cart"
                } else if (binding.content.btnAddCart.text.equals("Remove From Cart")) {
                    viewModel.addOrRemoveFromCart(productPojoParcelable.id)
                    binding.content.btnAddCart.text = "Add To Cart"
                }
            }
        }

        binding.content.icFavorite.setOnClickListener {
            if (!getUserToken) {
                CustomAlertDialog.alertDialog(requireContext(), this@ProductDetailsFragment, direction)
            } else {
                if (y == 0) {
                    viewModel.addOrRemoveFromFavorites(productPojoParcelable.id)
                    binding.content.icFavorite.setImageResource(R.drawable.ic_favorite_active)
                    y++
                } else if (y == 1) {
                    viewModel.addOrRemoveFromFavorites(productPojoParcelable.id)
                    binding.content.icFavorite.setImageResource(R.drawable.ic_favorite_disactive)
                    y = 0
                }
            }
        }

        binding.content.cvFavorite.setOnClickListener {
            viewModel.addOrRemoveFromFavorites(productPojoParcelable.id)
        }
    }

    private val onProductClick = object : ProductOnClick {
        override fun onAddRemoveProductFavoriteClick(product: ProductPojo) {
            viewModel.addOrRemoveFromFavorites(productPojoParcelable.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        productsAdapter.clearList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.d("_Binding::${_binding}")
    }

    override fun onResume() {
        super.onResume()
        _binding = binding
        binding.groupViews.visibility = View.GONE
        binding.loading.loading.visibility = View.VISIBLE
    }

    private fun setupViewPager() {
        // You need to retain one page on each side so that the next and previous items are visible
        binding.content.vpProImages.offscreenPageLimit = 1

        // Add a PageTransformer that translates the next and previous items horizontally
        // towards the center of the screen, which makes them visible
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        binding.content.vpProImages.setPageTransformer(pageTransformer)

        // The ItemDecoration gives the current (centered) item horizontal margin so that
        // it doesn't occupy the whole screen width. Without it the items overlap
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.content.vpProImages.addItemDecoration(itemDecoration)
    }
}