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

class ShopCategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelShop: ShopCategoriesViewModel
    private lateinit var categoryAdapter: ShopCategoriesAdapter

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
        request()
        observerData()
    }

    private fun init() {
        binding.loading.loading.visibility = View.VISIBLE
        binding.shopCategoryViewGroup.visibility = View.GONE

        binding.rvCategories.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = getString(R.string.shopCategory_fragment)
    }

    private fun request() {
        viewModelShop.getShopCategories()
    }

    private fun observerData() {
        viewModelShop.shopCategoryMutable.observe(viewLifecycleOwner, { categories ->
            if (categories.status) {
                categoryAdapter = ShopCategoriesAdapter(categories.categoriesData.categoriesData)
                binding.rvCategories.adapter = categoryAdapter
                binding.loading.loading.visibility = View.GONE
                binding.shopCategoryViewGroup.visibility = View.VISIBLE
            } else {
                binding.loading.loading.visibility = View.GONE
                binding.shopCategoryViewGroup.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}