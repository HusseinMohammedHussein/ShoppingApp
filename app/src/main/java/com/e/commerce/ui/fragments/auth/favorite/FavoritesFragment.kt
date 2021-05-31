package com.e.commerce.ui.fragments.auth.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.databinding.FragmentFavoritesBinding
import com.e.commerce.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private var viewModel: FavoritesViewModel = FavoritesViewModel()
    private val favoritesAdapter: FavoritesAdapter = FavoritesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FavoritesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methods()
    }

    private fun methods() {
        setupToolbar()
        init()
        observerData()
        onClickListener()
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { (requireActivity() as MainActivity).onBackPressed() }
        binding.appbar.tvTitle.text = "Favorites"
    }

    private fun init() {
        binding.loading.loading.visibility = View.VISIBLE
        binding.content.rvFavorites.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this.context, 2, LinearLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun onClickListener() {
        favoritesAdapter.onItemClick = { itemFavorite ->
            viewModel.removeFromFavorite(itemFavorite.product.id)
        }
    }

    private fun observerData() {
        viewModel.getFavorites()
        viewModel.favoriteMData.observe(viewLifecycleOwner, { response ->
            if (response.status) {
                favoritesAdapter.setData(response.data.productsFavorites)
                favoritesAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                binding.content.rvFavorites.adapter = null
            }
            binding.content.rvFavorites.visibility = View.VISIBLE
            binding.loading.loading.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Timber.i("FavoriteBindingIs::${_binding}")
    }
}