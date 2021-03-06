package com.e.commerce.ui.fragments.user.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.commerce.R
import com.e.commerce.data.model.auth.favorite.FavoritesDataPojo.FavoriteDataPojo
import com.e.commerce.databinding.FragmentFavoritesBinding
import com.e.commerce.ui.fragments.user.favorite.FavoritesAdapter.FavoriteItemClick
import com.e.commerce.ui.main.MainActivity
import timber.log.Timber

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoritesAdapter: FavoritesAdapter

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
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appbar.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.appbar.toolbar.setNavigationIcon(R.drawable.ic_back_row)
        binding.appbar.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.appbar.tvTitle.text = getString(R.string.favorites_fragment)
    }

    private fun init() {
        favoritesAdapter = FavoritesAdapter(onFavoriteClick)
        binding.loading.loading.visibility = View.VISIBLE
        binding.content.rvFavorites.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this.context, 2, LinearLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private val onFavoriteClick = object : FavoriteItemClick {
        override fun onFavoriteItemClick(favorite: FavoriteDataPojo) {
            viewModel.removeFromFavorite(favorite.product.id)
        }
    }

    private fun observerData() {
        viewModel.getFavorites()
        viewModel.favoriteMData.observe(viewLifecycleOwner, { response ->
            if (response.status) {
                favoritesAdapter.let { adapter ->
                    adapter.setData(response.favoriteDataPojo.favoriteProductsData)
                    adapter.notifyDataSetChanged()
                }
            } else {
                binding.noauth.tvNoAuthMsg.text = response.message
                binding.noauth.tvNoAuthMsg.visibility = View.VISIBLE
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