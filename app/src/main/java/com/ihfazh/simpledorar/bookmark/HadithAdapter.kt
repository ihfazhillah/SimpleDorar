package com.ihfazh.simpledorar.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorar.databinding.ItemCategoryHadithBinding
import com.ihfazh.simpledorar.utils.HadithUIHelper

class HadithAdapter: RecyclerView.Adapter<HadithAdapter.ViewHolder>() {
    private val items = mutableListOf<HadithBookmarkUI>()
    var onClick : (HadithBookmarkUI) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).let{ layoutInflater ->
            ItemCategoryHadithBinding.inflate(layoutInflater, parent, false)
        }.let{ binding ->
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let{ item ->
            holder.bind(item, onRootClick = onClick)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newItems: List<HadithBookmarkUI>){
        val callback = HadithCallback(this.items, newItems)
        val callbackResult = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(newItems)
        callbackResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemCategoryHadithBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(hadith: HadithBookmarkUI, onRootClick: (HadithBookmarkUI) -> Unit) {
            binding.apply {
//                refrenceTitle.text = "${hadith.mashdar} ${hadith.shafha}"
//                hadithExcerpt.text = hadith.rawText.take(200)
                result = hadith
                uiHelper = HadithUIHelper(binding.root.context)

                root.setOnClickListener {
                    onRootClick.invoke(hadith)
                }
            }
        }

    }

    inner class HadithCallback(val oldList: List<HadithBookmarkUI>, val newList: List<HadithBookmarkUI>):
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