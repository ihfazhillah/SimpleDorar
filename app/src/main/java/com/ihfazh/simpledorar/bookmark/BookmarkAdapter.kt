package com.ihfazh.simpledorar.bookmark

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.databinding.ItemCategoryBookmarkBinding

class BookmarkAdapter: RecyclerView.Adapter<BookmarkAdapter.ViewHolder>(){

    private val items = mutableListOf<BookmarkCategory>()
    var onItemClick: (BookmarkCategory, View) -> Unit = {_, _ ->}
    var onUpdateClick: (BookmarkCategory) -> Unit = {}
    var onDeleteClick: (BookmarkCategory) -> Unit = {}

    var sharedPool: RecyclerView.RecycledViewPool? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).let{ layoutInflater ->
            ItemCategoryBookmarkBinding.inflate(layoutInflater, parent, false)
        }.let{ binding ->
            if (sharedPool != null){
//                binding.childRv.setRecycledViewPool(sharedPool)
            }
            ViewHolder(binding).apply {
                onUpdateClick = this@BookmarkAdapter.onUpdateClick
                onDeleteClick = this@BookmarkAdapter.onDeleteClick
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let{ item ->
            holder.bind(item)
            holder.binding.root.setOnClickListener { onItemClick(item, holder.binding.title) }
            holder.binding.title.transitionName = "test" + item.id.toString()
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
        var onUpdateClick: (BookmarkCategory) -> Unit = {}
        var onDeleteClick: (BookmarkCategory) -> Unit = {}

        fun bind(bookmarkCategory: BookmarkCategory) {
            binding.apply {
                val context = root.context
                bookmark = bookmarkCategory
                btnMore.setOnClickListener {
                    val popup = PopupMenu(binding.root.context, btnMore)
                    popup.inflate(R.menu.bookmark_item_menu)
                    popup.setOnMenuItemClickListener {
                       when(it.itemId){
                           R.id.delete -> {
                               val dialogBuilder = AlertDialog.Builder(context).apply {
                                   setMessage(context.getString(R.string.delete_confirmation, bookmarkCategory.title))
                                   setCancelable(false)
                                   setPositiveButton(R.string.ok) { dialog, id ->
                                       onDeleteClick(bookmarkCategory)
                                       dialog.dismiss()
                                   }
                                   setNegativeButton(R.string.no){ dialog, id ->
                                       dialog.dismiss()
                                   }
                               }
                               dialogBuilder.create().show()
                              true
                           }
                           R.id.update -> {
                               onUpdateClick(bookmarkCategory)
                               true
                           }
                           else -> false
                       }
                    }
                    popup.show()
                }
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