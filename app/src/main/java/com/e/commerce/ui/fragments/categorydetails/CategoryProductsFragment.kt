package com.e.commerce.ui.fragments.categorydetails

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.data.model.category.CategoryItemPojo
import com.e.commerce.data.model.product.ProductPojo
import com.e.commerce.databinding.FragmentCategoryProductsBinding
import com.e.commerce.interfaces.OnCategoryProductClick
import com.e.commerce.ui.component.CustomAlertDialog
import com.e.commerce.ui.main.MainActivity
import com.e.commerce.util.SharedPref
import timber.log.Timber

class CategoryProductsFragment : Fragment() {
    private var _binding: FragmentCategoryProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: SharedPref
    private lateinit var categoryProductsVM: CategoryProductsVM
    private lateinit var productsCategoryParcelable: CategoryItemPojo
    private lateinit var productsCategoryAdapter: ProductsCategoryAdapter

    private var bundle: Bundle = Bundle()
    private var isUserLogin: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        categoryProductsVM = ViewModelProvider(requireActivity()).get(CategoryProductsVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        init()
        setupToolbar()
        request()
        observeData()
        setupSearch()
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = productsCategoryParcelable.name
        Timber.d("Title::${productsCategoryParcelable.name}")
    }

    private fun init() {
        productsCategoryAdapter = ProductsCategoryAdapter(onCategoryProductClick)
        bundle = requireArguments()
        productsCategoryParcelable = bundle.getParcelable(getString(R.string.category_pojo))!!
        sharedPref = SharedPref(requireContext())
        isUserLogin = sharedPref.getBoolean(getString(R.string.is_user))
        Timber.d("isUserLogin::$isUserLogin")


        binding.rvProducts.apply {
            setHasFixedSize(true)
            layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = productsCategoryAdapter
            visibility = View.GONE
        }
    }

    private fun request() {
        productsCategoryParcelable.id.let { categoryProductsVM.getCategoryProducts(it) }
        Timber.d("categoryId::${productsCategoryParcelable.id}")
        Timber.d("categoryName::${productsCategoryParcelable.name}")
    }

    private fun observeData() {
        categoryProductsVM.categoryProductsMutable.observe(viewLifecycleOwner, { response ->
            productsCategoryAdapter.setData(response.categoryData.products)
            binding.rvProducts.visibility = View.VISIBLE
            binding.loading.loading.visibility = View.GONE
        })

        categoryProductsVM.searchProductMutable.observe(viewLifecycleOwner, { response ->
            productsCategoryAdapter.setData(response.productsData.products)
            binding.loading.loading.visibility = View.GONE
            binding.rvProducts.visibility = View.VISIBLE
        })
    }

    private fun setupSearch() {
        binding.tvSearch.setOnClickListener {
            val keyword = binding.etSearch.text.toString().trim()
            categoryProductsVM.searchProduct(keyword)
            Timber.d("KeywordForSearch::${keyword}")
            binding.loading.loading.visibility = View.VISIBLE
            binding.rvProducts.visibility = View.GONE
        }
    }

    private val onCategoryProductClick = object : OnCategoryProductClick {
        override fun onItemClick(product: ProductPojo) {
            if (!isUserLogin) {
                val direction = CategoryProductsFragmentDirections.actionProductsCategoryToSignup()
                CustomAlertDialog.alertDialog(requireContext(), this@CategoryProductsFragment, direction)
            } else {
                categoryProductsVM.addRemoveFavorite(product.id)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.categories_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                productsCategoryAdapter.clearData()
                binding.rlSearch.visibility = View.VISIBLE
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.loading.loading.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}