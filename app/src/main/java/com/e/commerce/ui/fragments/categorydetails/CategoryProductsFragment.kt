package com.e.commerce.ui.fragments.categorydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewModel = ViewModelProvider(requireActivity()).get(CategoryDetailsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        init()
        setupToolbar()
        observeData()
    }

    private fun setupToolbar() {
        (activity as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
        viewModel.getCategoryDetails(categoryProductsParcelable.id)
        Timber.d("categoryId::${categoryProductsParcelable.id}")
        Timber.d("categoryName::${categoryProductsParcelable.name}")

        viewModel.categoryDetailsMutable.observe(viewLifecycleOwner, { categoryDetials ->
            productsAdapter.setData(categoryDetials.data.Products)
            productsAdapter.notifyDataSetChanged()
            binding.rvProducts.visibility = View.VISIBLE
            binding.loading.loading.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        productsAdapter.clearData()
    }

    override fun onResume() {
        super.onResume()
        binding.loading.loading.visibility = View.VISIBLE
    }
}