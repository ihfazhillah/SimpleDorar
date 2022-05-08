package com.ihfazh.simpledorar.bookmark.listExapandable

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorar.databinding.ItemCategoryBookmarkBinding

class BookmarkAdapter: RecyclerView.Adapter<BookmarkAdapter.ViewHolder>(){

    private val items = mutableListOf<BookmarkItemUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).let{ layoutInflater ->
            ItemCategoryBookmarkBinding.inflate(layoutInflater)
        }.let{ binding ->
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let{ item ->
            holder.bind(item)
            holder.binding.root.setOnClickListener {
                val newItem = item.copy(expand = !item.expand)
                submitList(items.map{
                    if (newItem.bookmarkCategory.id == it.bookmarkCategory.id){
                        newItem
                    } else {
                        it
                    }
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newItems: List<BookmarkItemUI>){
        val callback = BookmarkCallback(this.items, newItems)
        val callbackResult = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(newItems)
        callbackResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemCategoryBookmarkBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(bookmarkItemUI: BookmarkItemUI) {
            binding.apply {
                bookmark = bookmarkItemUI
//                bookmarkTitle.text = bookmarkItemUI.bookmarkCategory.title
//                bookmarkItemCount.text = bookmarkItemUI.items.size.toString()
                Log.d("ViewHolder BookmarkAdapter", "bind: view holder binding $bookmarkItemUI")

                // TODO: handle on buttons click listener

                HadithAdapter().let{ adapter ->
                    childRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
                    childRv.adapter = adapter
                    adapter.submitList(bookmarkItemUI.items)
                }
            }
        }

    }

    inner class BookmarkCallback(val oldList: List<BookmarkItemUI>, val newList: List<BookmarkItemUI>):
        DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].bookmarkCategory.id == newList[newItemPosition].bookmarkCategory.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old.expand == new.expand && old.bookmarkCategory == new.bookmarkCategory
        }
    }

}
