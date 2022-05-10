package com.ihfazh.simpledorar.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import com.ihfazh.simpledorar.databinding.BottomSheetBookmarkCategoryFormBinding
import com.ihfazh.simpledorar.databinding.BottomSheetBookmarkHadithBinding
import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.SearchResultDetailViewModel

class BookmarkHadithFromBottomSheet(private val item: BookmarkCategory): BottomSheetDialogFragment() {
    private var binding : BottomSheetBookmarkCategoryFormBinding? = null
    private val viewModel: BookmarkHadithBottomSheetFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.setBookmark(item)

        binding = BottomSheetBookmarkCategoryFormBinding.inflate(inflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            ok.setOnClickListener {
                viewModel.updateBookmark()
                dismiss()
            }

            categoryInput.setOnEditorActionListener { _, actionId, _ ->
                // true means the text view focus persist
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    viewModel.updateBookmark()
                    dismiss()
                }
                false
            }
        }
        return binding?.root
    }

    companion object {
        val TAG: String = BookmarkHadithFromBottomSheet::class.java.simpleName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}