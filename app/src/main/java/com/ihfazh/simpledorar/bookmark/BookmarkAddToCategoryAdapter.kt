package com.ihfazh.simpledorar.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorar.databinding.ItemAddToCategoryBinding

class BookmarkAddToCategoryAdapter: RecyclerView.Adapter<BookmarkAddToCategoryAdapter.ViewHolder>() {

    val items = mutableListOf<BookmarkCategory>()
    var onItemClick : (BookmarkCategory) -> Unit = {}

    fun setItems(newItems: List<BookmarkCategory>){
        val callback = ChangeCallback(items, newItems)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)
        items.clear()
        items.addAll(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddToCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(val binding: ItemAddToCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: BookmarkCategory, onItemClick: (BookmarkCategory) -> Unit) {
            binding.categoryText.text = category.title
            binding.root.setOnClickListener { onItemClick(category) }
        }

    }

    class ChangeCallback(private val old: List<BookmarkCategory>, private val new: List<BookmarkCategory>): DiffUtil.Callback(){
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
            return old == new
        }

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        onItemClick = {}
        super.onDetachedFromRecyclerView(recyclerView)
    }

}