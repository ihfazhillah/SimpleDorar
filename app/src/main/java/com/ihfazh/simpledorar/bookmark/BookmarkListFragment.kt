package com.ihfazh.simpledorar.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.dorar.HadithItem
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.bookmark.listExapandable.BookmarkAdapter
import com.ihfazh.simpledorar.bookmark.listExapandable.BookmarkItemUI
import com.ihfazh.simpledorar.databinding.FragmentBookmarkListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentBookmarkListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val items = listOf(
                BookmarkItemUI(
                    BookmarkCategory(1, "hello"),
                    true,
                    listOf(
                        HadithBookmark(1, "ab", "cd", "e", "f", "g", "h", BookmarkCategory(1, "ab"),),
                        HadithBookmark(1, "ab", "cd", "e", "f", "g", "h", BookmarkCategory(1, "ab"),),
                    )
                ),
                BookmarkItemUI(
                    BookmarkCategory(2, "hello"),
                    true,
                    listOf(
                        HadithBookmark(1, "ab", "cd", "e", "f", "g", "h", BookmarkCategory(1, "ab"),),
                        HadithBookmark(1, "ab", "cd", "e", "f", "g", "h", BookmarkCategory(1, "ab"),),
                    )
                ),
                BookmarkItemUI(
                    BookmarkCategory(3, getString(R.string.copy)),
                    true,
                    listOf(
                        HadithBookmark(1, "ab", "cd", "e", "f", "g", "h", BookmarkCategory(1, "ab"),),
                        HadithBookmark(1, "ab", "cd", "e", "f", "g", "h", BookmarkCategory(1, "ab"),),
                    )
                ),
                BookmarkItemUI(
                    BookmarkCategory(4, "hello"),
                    true,
                    listOf(
                        HadithBookmark(1, "ab", "cd", "e", "f", "g", "h", BookmarkCategory(1, "ab"),),
                        HadithBookmark(1, "ab", "cd", "e", "f", "g", "h", BookmarkCategory(1, "ab"),),
                    )
                ),
                BookmarkItemUI(
                    BookmarkCategory(5, "hello"),
                    true,
                    listOf(
                        HadithBookmark(1, "ab", "cd", "e", "f", "g", "h", BookmarkCategory(1, "ab"),),
                        HadithBookmark(1, "ab", "cd", "e", "f", "g", "h", BookmarkCategory(1, "ab"),),
                    )
                ),
            )
            val adapter = BookmarkAdapter()
            bookmarkList.adapter = adapter
            bookmarkList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter.submitList(items)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookmarkListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookmarkListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}