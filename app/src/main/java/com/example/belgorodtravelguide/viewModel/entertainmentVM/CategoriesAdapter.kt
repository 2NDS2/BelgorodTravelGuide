package com.example.belgorodtravelguide.viewModel.entertainmentVM

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.belgorodtravelguide.databinding.CardItemCategoryBinding
import com.example.belgorodtravelguide.databinding.ItemHeaderBinding
import com.example.belgorodtravelguide.model.modelEntertainment.CategoriesListData
import com.example.belgorodtravelguide.model.modelEntertainment.HeaderData

class CategoriesAdapter(
    private val items: List<Any>,
    private val onItemClicked: (CategoriesListData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }
            TYPE_ITEM -> {
                val binding = CardItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CategoryViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val header = items[position] as HeaderData
                holder.bind(header)
            }
            is CategoryViewHolder -> {
                val category = items[position] as CategoriesListData
                holder.bind(category)
                holder.itemView.setOnClickListener {
                    onItemClicked(category)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HeaderData -> TYPE_HEADER
            is CategoriesListData -> TYPE_ITEM
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    //ViewHolder для Header
    inner class HeaderViewHolder(private val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: HeaderData) {
            binding.headerTitle.text = header.title
        }
    }
    //ViewHolder для категорий
    inner class CategoryViewHolder(private val binding: CardItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoriesListData) {
            binding.imgCat.setImageResource(category.imgCat)
            binding.nameCat.text = category.nameCat
        }
    }
}
