package com.ihfazh.simpledorar.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorar.databinding.ItemAddToCategoryBinding

class BookmarkAddToCategoryAdapter(diffCallback: DiffUtil.ItemCallback<BookmarkCategory>): PagingDataAdapter<BookmarkCategory, BookmarkAddToCategoryAdapter.ViewHolder>(diffCallback) {

    var onItemClick : (BookmarkCategory) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddToCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item, onItemClick)
        }
    }

    class ViewHolder(val binding: ItemAddToCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: BookmarkCategory, onItemClick: (BookmarkCategory) -> Unit) {
            binding.categoryText.text = category.title
            binding.root.setOnClickListener { onItemClick(category) }
        }

    }

    object ChangeCallback: DiffUtil.ItemCallback<BookmarkCategory>(){
        override fun areItemsTheSame(
            oldItem: BookmarkCategory,
            newItem: BookmarkCategory
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BookmarkCategory,
            newItem: BookmarkCategory
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        onItemClick = {}
        super.onDetachedFromRecyclerView(recyclerView)
    }

}