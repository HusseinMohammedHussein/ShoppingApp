package com.e.commerce.ui.fragments.categorydetails

import android.os.Bundle
import android.view.*
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.data.model.home.CategoryPojo.Data.CategoriesPojo
import com.e.commerce.databinding.FragmentCategoryDetailsBinding
import com.e.commerce.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CategoryProductsFragment : Fragment() {
    private lateinit var binding: FragmentCategoryDetailsBinding
    private lateinit var bundle: Bundle

    private lateinit var categoryProductsParcelable: CategoriesPojo

    private var viewModel: CategoryDetailsViewModel = CategoryDetailsViewModel()
    private var productsAdapter: CategoryProductsAdapter = CategoryProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(requireActivity()).get(CategoryDetailsViewModel::class.java)
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
        onCLickListener()
    }

    private fun onCLickListener() {
        productsAdapter.onItemClick = { product ->
            viewModel.addRemoveFavorite(product.id)
        }
    }

    private fun request() {
        viewModel.getCategoryDetails(categoryProductsParcelable.id)
        Timber.d("categoryId::${categoryProductsParcelable.id}")
        Timber.d("categoryName::${categoryProductsParcelable.name}")
    }

    private fun setupSearch() {
        binding.tvSearch.setOnClickListener {
            val keyword = binding.etSearch.text.toString().trim()
            viewModel.searchProduct(keyword)
            Timber.d("KeywordForSearch::${keyword}")
            binding.loading.loading.visibility = View.VISIBLE
            binding.rvProducts.visibility = View.GONE
        }
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { (activity as MainActivity).onBackPressed() }
        binding.appbar.tvTitle.text = categoryProductsParcelable.name
        Timber.d("Title::${categoryProductsParcelable.name}")
    }

    private fun init() {
        bundle = Bundle()
        bundle = requireArguments()
        categoryProductsParcelable = bundle.getParcelable(resources.getString(R.string.category_pojo))!!
        binding.rvProducts.apply {
            setHasFixedSize(true)
            layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = productsAdapter
        }
    }

    private fun observeData() {
        viewModel.categoryDetailsMutable.observe(viewLifecycleOwner, { categoryDetials ->
            productsAdapter.setData(categoryDetials.data.Products)
            productsAdapter.notifyDataSetChanged()
            binding.rvProducts.visibility = View.VISIBLE
            binding.loading.loading.visibility = View.GONE
        })

        viewModel.searchProductMutable.observe(viewLifecycleOwner, { searchResponse ->
            binding.loading.loading.visibility = View.GONE
            binding.rvProducts.visibility = View.VISIBLE
            productsAdapter.setData(searchResponse.data.products)
            productsAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.categories_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                productsAdapter.clearData()
                binding.rlSearch.visibility = View.VISIBLE
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (binding.rvProducts.isNotEmpty()) {
            productsAdapter.clearData()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.loading.loading.visibility = View.VISIBLE
    }
}