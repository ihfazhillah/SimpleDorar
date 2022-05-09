package com.ihfazh.simpledorar.bookmark.listExapandable

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.databinding.ItemCategoryBookmarkBinding

class BookmarkAdapter: RecyclerView.Adapter<BookmarkAdapter.ViewHolder>(){

    private val items = mutableListOf<BookmarkCategory>()
    var onItemClick: (BookmarkItemUI) -> Unit = {}
    var sharedPool: RecyclerView.RecycledViewPool? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).let{ layoutInflater ->
            ItemCategoryBookmarkBinding.inflate(layoutInflater, parent, false)
        }.let{ binding ->
            if (sharedPool != null){
//                binding.childRv.setRecycledViewPool(sharedPool)
            }
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let{ item ->
            holder.bind(item)
//            holder.binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newItems: List<BookmarkCategory>){
        val callback = BookmarkCallback(this.items, newItems)
        val callbackResult = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(newItems)
        callbackResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemCategoryBookmarkBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(bookmarkCategory: BookmarkCategory) {
            binding.apply {
                bookmark = bookmarkCategory
//                bookmarkTitle.text = bookmarkItemUI.bookmarkCategory.title
//                bookmarkItemCount.text = bookmarkItemUI.items.size.toString()
//                Log.d("ViewHolder BookmarkAdapter", "bind: view holder binding $bookmarkItemUI")

                // TODO: handle on buttons click listener

//                HadithAdapter().let{ innerAdapter ->
//                    childRv.apply{
//                        adapter = innerAdapter
//                        layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false).apply {
//                            initialPrefetchItemCount = 3
//                        }
//                    }
//                    innerAdapter.submitList(bookmarkItemUI.items)
//                }
            }
        }

    }

    inner class BookmarkCallback(val oldList: List<BookmarkCategory>, val newList: List<BookmarkCategory>):
        DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old == new
        }
    }

}
