package com.ihfazh.simpledorar.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorar.databinding.ItemSearchResultBinding

class SearchResultRecyclerViewAdapter(private val viewModel: SearchViewModel):
    RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder>() {

    private var items: List<ResultItem> = listOf()

    fun submitList(items: List<ResultItem>){
        val callback = SearchQueryDiffCallback(this.items, items)
        val result = DiffUtil.calculateDiff(callback)
        this.items = items
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context)).apply {
        }
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

    inner class SearchQueryDiffCallback(private val old: List<ResultItem>, private val new: List<ResultItem>):
        DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return old.size
        }

        override fun getNewListSize(): Int {
            return new.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition].rawText == new[newItemPosition].rawText
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]
            return oldItem.rawText == newItem.rawText && oldItem.mashdar == newItem.mashdar
        }

    }

    class ViewHolder(private val binding: ItemSearchResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(resultItem: ResultItem) {
            binding.item = resultItem
            binding.root.setOnClickListener {
                val destination = SearchResultDetailDirections.goToSearchResultDetail("", resultItem)
                it.findNavController().navigate(destination)
            }
        }

    }
}