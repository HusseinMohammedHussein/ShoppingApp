package com.e.commerce.ui.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.databinding.FragmentCategoriesBinding
import com.e.commerce.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopCategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private var viewModelShop: ShopCategoriesViewModel = ShopCategoriesViewModel()

    private var categoryAdapter: ShopCategoriesAdapter = ShopCategoriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelShop = ViewModelProvider(requireActivity()).get(ShopCategoriesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        setupToolbar()
        init()
        observerData()
    }

    private fun init() {
        binding.loading.loading.visibility = View.VISIBLE
        binding.shopCategoryViewGroup.visibility = View.GONE

        binding.rvCategories.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = getString(R.string.Title_ShopCategory)
    }

    private fun observerData() {
        viewModelShop.getShopCategories()
        viewModelShop.shopCategoryMutable.observe(viewLifecycleOwner, { categories ->
            categoryAdapter.setData(categories.data.categories)
            categoryAdapter.notifyDataSetChanged()
            binding.loading.loading.visibility = View.GONE
            binding.shopCategoryViewGroup.visibility = View.VISIBLE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}