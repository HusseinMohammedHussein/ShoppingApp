package com.e.commerce.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.e.commerce.R
import com.e.commerce.data.model.home.CategoryPojo.Data.CategoriesPojo
import com.e.commerce.databinding.ItemCategoryBinding
import com.squareup.picasso.Picasso
import timber.log.Timber

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private lateinit var pojoList: List<CategoriesPojo>

    fun setData(pojos: List<CategoriesPojo>) {
        pojos.also { this.pojoList = it }
    }

    inner class CategoryViewHolder(var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pojo: CategoriesPojo) {
            binding.tvCategoryName.text = pojo.name

            Picasso.get()
                .load(pojo.image)
                .into(binding.imgCategory)

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(it.context.resources.getString(R.string.category_pojo), pojo)

                Timber.d("itemCategory::${pojo.id}")

                itemView.findNavController().navigate(R.id.action_category_to_categoryDetails, bundle)
            }
        }
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

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(pojoList[position])
    }

    override fun getItemCount(): Int {
        return pojoList.size
    }
}