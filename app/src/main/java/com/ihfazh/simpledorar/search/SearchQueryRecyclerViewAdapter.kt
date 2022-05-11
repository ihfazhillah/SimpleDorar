package com.ihfazh.simpledorar.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorar.databinding.ItemSearchHistoryBinding

class SearchQueryRecyclerViewAdapter(private val viewModel: SearchViewModel):
    RecyclerView.Adapter<SearchQueryRecyclerViewAdapter.ViewHolder>() {

    private var items: List<SearchQuery> = listOf()

    var onClickItem: (SearchQuery) -> Unit = {}

    fun submitList(items: List<SearchQuery>){
        val callback = SearchQueryDiffCallback(this.items, items)
        val result = DiffUtil.calculateDiff(callback)
        this.items = items
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            vm = viewModel
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
        holder.binding.root.setOnClickListener {
            onClickItem.invoke(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class SearchQueryDiffCallback(private val old: List<SearchQuery>, private val new: List<SearchQuery>):
        DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return old.size
        }

        override fun getNewListSize(): Int {
            return new.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition].id == new[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition].id == new[newItemPosition].id
        }

    }

    class ViewHolder(val binding: ItemSearchHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(searchQuery: SearchQuery) {
            binding.searchQuery = searchQuery
        }

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        onClickItem = {}
        super.onDetachedFromRecyclerView(recyclerView)
    }
}