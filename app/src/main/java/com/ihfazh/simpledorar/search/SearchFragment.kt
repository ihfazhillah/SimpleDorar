package com.ihfazh.simpledorar.search

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.view_search_history.view.*

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var viewBinding : FragmentSearchBinding
    private lateinit var searchQueryRVAdapter: SearchQueryRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchQueryRVAdapter = SearchQueryRecyclerViewAdapter(viewModel)

        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner

            searchTextInput.setOnEditorActionListener { _, actionId, _ ->
                // true means the text view focus persist
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    viewModel.search()
                    true
                } else {
                    false
                }
            }
            searchContainer.setEndIconOnClickListener {
                viewModel.backToHistory()
            }

            searchHistory.vm = viewModel

            searchHistory.searchHistory.searchHistoryRv.apply {
                adapter = searchQueryRVAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }

        viewModel.searchState.observe(viewLifecycleOwner){state ->
            viewBinding.searchState = state
            Log.d(TAG, "Current view State = $state")
            toggleViewByState(state)
        }

        if (this::searchQueryRVAdapter.isInitialized){
            viewModel.histories.observe(viewLifecycleOwner){
                searchQueryRVAdapter.submitList(it)
            }
        }
    }

    private fun toggleViewByState(state: SearchState?) {
        val views = listOf(
            viewBinding.searchHistory
        )

        val viewMaps = mapOf(
            SearchState.HasHistory to viewBinding.searchHistory
        )

        state?.let {
            val view = viewMaps[it]
            views.forEach { v ->
                if (v == view){
                    v.root.visibility = View.VISIBLE
                } else {
                    v.root.visibility = View.INVISIBLE
                }

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentSearchBinding.inflate(inflater).apply {
            vm = viewModel
        }
        return viewBinding.root
    }

    companion object {
        private val TAG = SearchFragment::class.java.simpleName
    }
}