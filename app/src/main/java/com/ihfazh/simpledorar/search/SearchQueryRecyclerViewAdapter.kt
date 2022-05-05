package com.ihfazh.simpledorar.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorar.databinding.ItemSearchHistoryBinding

class SearchQueryRecyclerViewAdapter:
    RecyclerView.Adapter<SearchQueryRecyclerViewAdapter.ViewHolder>() {

    private var items: List<SearchQuery> = listOf()

    class ViewHolder(private val binding: ItemSearchHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(searchQuery: SearchQuery) {
            binding.searchQuery = searchQuery
        }

    }

    fun submitList(items: List<SearchQuery>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}