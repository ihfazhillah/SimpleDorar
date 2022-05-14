package com.ihfazh.simpledorarnew.bookmark

import android.app.AlertDialog
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorarnew.R
import com.ihfazh.simpledorarnew.databinding.ItemCategoryBookmarkBinding

class BookmarkAdapter(diffCallback: DiffUtil.ItemCallback<BookmarkCategory>): PagingDataAdapter<BookmarkCategory, BookmarkAdapter.ViewHolder>(diffCallback){

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
        getItem(position)?.let{ item ->
            holder.bind(item)
            holder.binding.root.setOnClickListener { onItemClick(item, holder.binding.title) }
            holder.binding.title.transitionName = "test" + item.id.toString()
        }
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        popup.setForceShowIcon(true)
                    }
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

    object BookmarkCallback: DiffUtil.ItemCallback<BookmarkCategory>(){
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


}
