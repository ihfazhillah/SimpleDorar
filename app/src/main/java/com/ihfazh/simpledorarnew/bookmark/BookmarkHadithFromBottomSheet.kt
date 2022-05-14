package com.ihfazh.simpledorarnew.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ihfazh.simpledorarnew.databinding.BottomSheetBookmarkCategoryFormBinding

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