package com.ihfazh.simpledorarnew.bookmark

import android.app.AlertDialog
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorarnew.R
import com.ihfazh.simpledorarnew.databinding.ItemCategoryHadithBinding
import com.ihfazh.simpledorarnew.utils.HadithUIHelper

class HadithAdapter(diffCallback: DiffUtil.ItemCallback<HadithBookmarkUI>):
    PagingDataAdapter<HadithBookmarkUI, HadithAdapter.ViewHolder>(diffCallback) {
    var onClick : (HadithBookmarkUI) -> Unit = {}
    var onRemoveClick: (HadithBookmarkUI) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).let{ layoutInflater ->
            ItemCategoryHadithBinding.inflate(layoutInflater, parent, false)
        }.let{ binding ->
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let{ item ->
            holder.bind(item, onRootClick = onClick, onRemoveClick = onRemoveClick)
        }
    }


    class ViewHolder(val binding: ItemCategoryHadithBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(hadith: HadithBookmarkUI, onRootClick: (HadithBookmarkUI) -> Unit, onRemoveClick: (HadithBookmarkUI) -> Unit) {
            binding.apply {
//                refrenceTitle.text = "${hadith.mashdar} ${hadith.shafha}"
//                hadithExcerpt.text = hadith.rawText.take(200)
                result = hadith
                uiHelper = HadithUIHelper(binding.root.context)

                root.setOnClickListener {
                    onRootClick.invoke(hadith)
                }

                btnMore.setOnClickListener {
                    val context = it.context
                    val popup = PopupMenu(binding.root.context, btnMore)
                    popup.inflate(R.menu.item_delete)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        popup.setForceShowIcon(true)
                    }
                    popup.setOnMenuItemClickListener { menu ->
                        when(menu.itemId){
                            R.id.delete -> {
                                val dialogBuilder = AlertDialog.Builder(context).apply {
                                    setMessage(context.getString(R.string.delete_confirmation, HadithUIHelper(context).setReference(hadith.hadithBookmark)))
                                    setCancelable(false)
                                    setPositiveButton(R.string.ok) { dialog, id ->
                                        onRemoveClick(hadith)
                                        dialog.dismiss()
                                    }
                                    setNegativeButton(R.string.no){ dialog, id ->
                                        dialog.dismiss()
                                    }
                                }
                                dialogBuilder.create().show()
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

    object HadithCallback: DiffUtil.ItemCallback<HadithBookmarkUI>(){
        override fun areContentsTheSame(
            oldItem: HadithBookmarkUI,
            newItem: HadithBookmarkUI
        ): Boolean {
            return oldItem.hadithBookmark.id == newItem.hadithBookmark.id
        }

        override fun areItemsTheSame(
            oldItem: HadithBookmarkUI,
            newItem: HadithBookmarkUI
        ): Boolean {
            return oldItem == newItem
        }
    }


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        onRemoveClick = {}
        super.onDetachedFromRecyclerView(recyclerView)
    }

}