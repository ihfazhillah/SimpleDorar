package com.ihfazh.simpledorar.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.simpledorar.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.view_search_history.view.*

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var viewBinding : FragmentSearchBinding
    private lateinit var searchQueryRVAdapter: SearchQueryRecyclerViewAdapter
    private lateinit var searchResultRVAdapter: SearchResultRecyclerViewAdapter

    private val speakContract = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        val results = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        if (!results.isNullOrEmpty()){
            viewModel.query.value = results[0]
            viewModel.search(forceNewRequest = true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner

            searchTextInput.setOnEditorActionListener { _, actionId, _ ->
                // true means the text view focus persist
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    viewModel.search(forceNewRequest = true)
                    true
                } else {
                    false
                }
            }
            searchContainer.setEndIconOnClickListener {
                viewModel.backToHistory()
            }

            // Speech to text
            btnSpeak.setOnClickListener {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA")
                }
                speakContract.launch(intent)
            }

            // SEARCH HISTORY SECTION
            searchQueryRVAdapter = SearchQueryRecyclerViewAdapter(viewModel)
            searchQueryRVAdapter.onClickItem = { query ->
                viewModel.search(query.query)
                searchTextInput.requestFocus()
                val IManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                IManager.showSoftInput(searchTextInput, InputMethodManager.SHOW_IMPLICIT)
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


            // SEARCH RESULT SECTION
            searchResultRVAdapter = SearchResultRecyclerViewAdapter(viewModel)
            searchResults.vm = viewModel
            searchResults.searchResultRv.apply {
                adapter = searchResultRVAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
            searchResults.next.setOnClickListener {
                viewModel.searchNext()
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

        if (this::searchResultRVAdapter.isInitialized){
            viewModel.searchResults.observe(viewLifecycleOwner){
                searchResultRVAdapter.submitList(it)
            }
        }
    }

    private fun toggleViewByState(state: SearchState?) {
        val views = listOf(
            viewBinding.searchHistory,
            viewBinding.searchResults
        )

        val viewMaps = mapOf(
            SearchState.HasHistory to viewBinding.searchHistory,
            SearchState.SearchResult to viewBinding.searchResults
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