package com.ihfazh.simpledorarnew.bookmark

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ihfazh.simpledorarnew.R
import com.ihfazh.simpledorarnew.databinding.BottomSheetBookmarkHadithBinding
import com.ihfazh.simpledorarnew.search.ResultItem
import com.ihfazh.simpledorarnew.search.SearchResultDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookmarkHadithBottomSheet(private val item: ResultItem): BottomSheetDialogFragment() {
    private var binding: BottomSheetBookmarkHadithBinding? = null
    private val viewModel: SearchResultDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as BottomSheetDialog).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bookmarkAdapter = BookmarkAddToCategoryAdapter(BookmarkAddToCategoryAdapter.ChangeCallback).apply {
            onItemClick = {
                viewModel.setSelectedBookmark(it)
                viewModel.saveHadith(item)
                dismiss()
            }
        }
        binding = BottomSheetBookmarkHadithBinding.inflate(inflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            addToCategoryRv.apply{
                adapter = bookmarkAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            btnAddNewBookmark.setOnClickListener {
                showAlertWithTextInputLayout(root.context)
            }
        }


        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.categories.collectLatest {pagingData ->
                bookmarkAdapter.submitData(pagingData)
            }
        }
        return binding?.root
    }

    companion object {
        val TAG: String = BookmarkHadithBottomSheet::class.java.simpleName
    }

    private fun showAlertWithTextInputLayout(context: Context) {
        val textInputLayout = TextInputLayout(context)
        textInputLayout.setPadding(
            resources.getDimensionPixelOffset(R.dimen.dp_19),
            0,
            resources.getDimensionPixelOffset(R.dimen.dp_19),
            0
        )
        val input = TextInputEditText(context)
        input.setText( viewModel.searchText.value?.toString() ?: "")
        textInputLayout.hint = "Bookmark Category"
        textInputLayout.addView(input)

        val alert = AlertDialog.Builder(context)
            .setTitle("Bookmark Category")
            .setView(textInputLayout)
            .setMessage("Create a new bookmark category")
            .setPositiveButton("Submit") { dialog, _ ->
                viewModel.setSelectedBookmark(BookmarkCategory(0, input.text.toString()))
                viewModel.searchText.value = input.text.toString()
                dialog.cancel()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.create()

        alert.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}