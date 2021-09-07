package com.e.commerce.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.commerce.R
import com.e.commerce.data.model.home.CategoryPojo.Data.CategoriesPojo
import com.e.commerce.databinding.ItemCategoryBinding
import timber.log.Timber

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var pojoList = ArrayList<CategoriesPojo>()

    fun setData(pojos: ArrayList<CategoriesPojo>) {
        pojos.also { this.pojoList = it }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) = holder.bind(pojoList[position])

    override fun getItemCount(): Int = pojoList.size

    inner class CategoryViewHolder(var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pojo: CategoriesPojo) {
            binding.tvCategoryName.text = pojo.name

            Glide.with(itemView)
                .load(pojo.image)
                .into(binding.imgCategory)

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(it.context.getString(R.string.category_pojo), pojo)
                Timber.d("itemCategory::${pojo.id}")
                itemView.findNavController().navigate(R.id.action_category_to_categoryDetails, bundle)
            }
        }
    }
}