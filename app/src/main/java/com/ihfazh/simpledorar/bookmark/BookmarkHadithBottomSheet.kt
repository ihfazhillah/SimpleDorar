package com.ihfazh.simpledorar.bookmark

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.databinding.BottomSheetBookmarkHadithBinding
import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.SearchResultDetailViewModel

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
        val bookmarkAdapter = BookmarkAddToCategoryAdapter().apply {
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

        viewModel.categories.observe(viewLifecycleOwner){
            Log.d(TAG, "onCreateView: list bookmark category $it")
            bookmarkAdapter.setItems(it)
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
        val input = EditText(context)
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