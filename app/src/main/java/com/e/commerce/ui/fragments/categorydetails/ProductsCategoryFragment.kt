package com.e.commerce.ui.fragments.categorydetails

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.data.model.home.CategoryPojo.Data.CategoriesPojo
import com.e.commerce.databinding.FragmentProductsCategoryBinding
import com.e.commerce.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProductsCategoryFragment : Fragment() {
    private var _binding: FragmentProductsCategoryBinding? = null
    private val binding get() = _binding!!
    private var productsCategoryVM: ProductsCategoryVM = ProductsCategoryVM()
    private var productsCategoryAdapter: ProductsCategoryAdapter? = null

    private var bundle: Bundle? = null
    private var productsCategoryParcelable: CategoriesPojo? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        productsCategoryVM = ViewModelProvider(requireActivity()).get(ProductsCategoryVM::class.java)
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

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = productsCategoryParcelable?.name
        Timber.d("Title::${productsCategoryParcelable?.name}")
    }

    private fun init() {
        bundle = Bundle()
        bundle = arguments
        productsCategoryAdapter = ProductsCategoryAdapter()
        productsCategoryParcelable = bundle?.getParcelable(getString(R.string.category_pojo))!!

        binding.rvProducts.apply {
            setHasFixedSize(true)
            layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = productsCategoryAdapter
            visibility = View.GONE
        }
    }

    private fun request() {
        productsCategoryParcelable?.id?.let { productsCategoryVM.getCategoryDetails(it) }
        Timber.d("categoryId::${productsCategoryParcelable?.id}")
        Timber.d("categoryName::${productsCategoryParcelable?.name}")
    }

    private fun observeData() {
        productsCategoryVM.categoryDetailsMutable.observe(viewLifecycleOwner, { response ->
            productsCategoryAdapter?.let { adapter ->
                adapter.setData(response.data.Products)
                adapter.notifyDataSetChanged()
            }
            binding.rvProducts.visibility = View.VISIBLE
            binding.loading.loading.visibility = View.GONE
        })

        productsCategoryVM.searchProductMutable.observe(viewLifecycleOwner, { response ->
            binding.loading.loading.visibility = View.GONE
            binding.rvProducts.visibility = View.VISIBLE
            productsCategoryAdapter?.let { adapter ->
                adapter.setData(response.data.products)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun setupSearch() {
        binding.tvSearch.setOnClickListener {
            val keyword = binding.etSearch.text.toString().trim()
            productsCategoryVM.searchProduct(keyword)
            Timber.d("KeywordForSearch::${keyword}")
            binding.loading.loading.visibility = View.VISIBLE
            binding.rvProducts.visibility = View.GONE
        }
    }

    private fun onCLickListener() {
        productsCategoryAdapter?.onItemClick = { product ->
            productsCategoryVM.addRemoveFavorite(product.id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.categories_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                productsCategoryAdapter?.clearData()
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