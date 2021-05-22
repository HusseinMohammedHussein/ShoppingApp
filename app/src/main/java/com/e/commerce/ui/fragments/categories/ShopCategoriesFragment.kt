package com.e.commerce.ui.fragments.categories

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.databinding.FragmentCategoriesBinding
import com.e.commerce.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopCategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private var viewModelShop: ShopCategoriesViewModel = ShopCategoriesViewModel()

    private var categoryAdapter: ShopCategoriesAdapter = ShopCategoriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModelShop =
            ViewModelProvider(requireActivity()).get(ShopCategoriesViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.categories_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                Toast.makeText(this.context, "Search", Toast.LENGTH_LONG).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

        binding.rvCategories.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun setupToolbar() {
        (activity as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = getString(R.string.Title_ShopCategory)
    }

    private fun observerData() {
        viewModelShop.getShopCategories()
        viewModelShop.shopCategoryMutable.observe(this.requireActivity(), { categories ->
            categoryAdapter.setData(categories.data.categories)
            categoryAdapter.notifyDataSetChanged()
            binding.loading.loading.visibility = View.GONE
            binding.shopCategoryViewGroup.visibility = View.VISIBLE
        })
    }
}