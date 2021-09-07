package com.e.commerce.ui.fragments.categories;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.commerce.R
import com.e.commerce.data.model.home.CategoryPojo.Data.CategoriesPojo
import com.e.commerce.databinding.ItemShopCategoryBinding

class ShopCategoriesAdapter : RecyclerView.Adapter<ShopCategoriesAdapter.CategoriesViewHolder>() {

    private var pojoListShop =  ArrayList<CategoriesPojo>()

    fun setData(pojoShops: ArrayList<CategoriesPojo>) {
        pojoShops.also { this.pojoListShop = it }
    }

    fun clearData() {
        pojoListShop.clear()
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(var binding: ItemShopCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pojoShop: CategoriesPojo) {
            binding.tvCategoryName.text = pojoShop.name

            Glide.with(itemView)
                .load(pojoShop.image)
                .into(binding.imgShopCategory)

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(it.context.resources.getString(R.string.category_pojo), pojoShop)
                itemView.findNavController().navigate(R.id.action_categories_to_catDetails, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemShopCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(pojoListShop[position])

    }

    override fun getItemCount(): Int = pojoListShop.size
}