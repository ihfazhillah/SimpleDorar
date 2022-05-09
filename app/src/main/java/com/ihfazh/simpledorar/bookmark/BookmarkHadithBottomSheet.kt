package com.ihfazh.simpledorar.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import com.ihfazh.simpledorar.databinding.BottomSheetBookmarkHadithBinding
import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.SearchResultDetailViewModel

class BookmarkHadithBottomSheet(private val item: ResultItem): BottomSheetDialogFragment() {
    private var binding: BottomSheetBookmarkHadithBinding? = null
    private val viewModel: SearchResultDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetBookmarkHadithBinding.inflate(inflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            val repository = DorarDatabase.getInstance(requireContext()).let { db ->
                LocalSearchRepository.getInstance(db)
            }
            val adapter = BookmarkCategoryCompleteAdapter(requireContext(),  repository)
            categoryInput.setAdapter(adapter)

            categoryInput.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
                val bookmarkCategory = parent.adapter.getItem(position) as BookmarkCategory
                categoryInput.setText(bookmarkCategory.title)
                viewModel.setSelectedBookmark(bookmarkCategory)
            }

            ok.setOnClickListener {
                viewModel.saveHadith(item)
                dismiss()
                requireParentFragment().findNavController().navigateUp()
            }
        }
        return binding?.root
    }

    companion object {
        val TAG: String = BookmarkHadithBottomSheet::class.java.simpleName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}