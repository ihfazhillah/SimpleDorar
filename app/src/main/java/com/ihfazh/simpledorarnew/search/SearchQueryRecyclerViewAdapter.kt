package com.ihfazh.simpledorarnew.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorarnew.databinding.ItemSearchHistoryBinding

class SearchQueryRecyclerViewAdapter(private val viewModel: SearchViewModel, diffCallback: DiffUtil.ItemCallback<SearchQuery>):
    PagingDataAdapter<SearchQuery, SearchQueryRecyclerViewAdapter.ViewHolder>(diffCallback) {

//    private var items: List<SearchQuery> = listOf()

    var onClickItem: (SearchQuery) -> Unit = {}

//    fun submitList(items: List<SearchQuery>){
//        val callback = SearchQueryDiffCallback(this.items, items)
//        val result = DiffUtil.calculateDiff(callback)
//        this.items = items
//        result.dispatchUpdatesTo(this)
//    }

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
        getItem(position)?.let{ item ->
            holder.bind(item)
            holder.binding.root.setOnClickListener {
                onClickItem.invoke(item)
            }
        }
    }

    object SearchQueryDiff: DiffUtil.ItemCallback<SearchQuery>() {
        override fun areItemsTheSame(oldItem: SearchQuery, newItem: SearchQuery): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchQuery, newItem: SearchQuery): Boolean {
            return oldItem == newItem
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