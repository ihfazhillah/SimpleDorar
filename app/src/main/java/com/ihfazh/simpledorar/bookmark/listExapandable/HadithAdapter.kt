package com.ihfazh.simpledorar.bookmark.listExapandable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorar.bookmark.HadithBookmark
import com.ihfazh.simpledorar.databinding.ItemCategoryHadithBinding

class HadithAdapter: RecyclerView.Adapter<HadithAdapter.ViewHolder>() {
    private val items = mutableListOf<HadithBookmark>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).let{ layoutInflater ->
            ItemCategoryHadithBinding.inflate(layoutInflater, parent, false)
        }.let{ binding ->
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let{ item ->
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newItems: List<HadithBookmark>){
        val callback = HadithCallback(this.items, newItems)
        val callbackResult = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(newItems)
        callbackResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemCategoryHadithBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(hadith: HadithBookmark) {
            binding.apply {
                refrenceTitle.text = "${hadith.mashdar} ${hadith.shafha}"
                hadithExcerpt.text = hadith.rawText.take(200)
            }
        }

    }

    inner class HadithCallback(val oldList: List<HadithBookmark>, val newList: List<HadithBookmark>):
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