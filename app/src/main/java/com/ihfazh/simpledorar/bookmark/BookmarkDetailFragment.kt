package com.ihfazh.simpledorar.bookmark

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.databinding.FragmentBookmarkDetailBinding
import com.ihfazh.simpledorar.note.NoteBottomSheet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding : FragmentBookmarkDetailBinding? = null
    private val viewModel by activityViewModels<BookmarkDetailViewModel>()
    private val args by navArgs<BookmarkDetailFragmentArgs>()
    private val hadithAdapter  = HadithAdapter(HadithAdapter.HadithCallback).apply {
        onRemoveClick = {
            viewModel.deleteHadith(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_category_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.note_toggle -> {
                val noteBottomSheet = NoteBottomSheet(args.id)
                noteBottomSheet.show(parentFragmentManager, NoteBottomSheet.TAG)
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        // Inflate the layout for this fragment
        binding = FragmentBookmarkDetailBinding.inflate(inflater, container, false).apply {
            rv.apply{
                adapter = hadithAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hadithAdapter.onClick = {
            viewModel.toggleExpandedHadithList(it.id)
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.getHadithList(args.id).collectLatest {
                hadithAdapter.submitData(it)
            }
        }

        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }

    }


    companion object {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}