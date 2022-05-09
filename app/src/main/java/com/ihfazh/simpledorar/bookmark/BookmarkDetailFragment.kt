package com.ihfazh.simpledorar.bookmark

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.databinding.FragmentBookmarkDetailBinding

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
    private val hadithAdapter  = HadithAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        viewModel.getHadithList(args.id).observe(viewLifecycleOwner){
            hadithAdapter.submitList(it)
            (view.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }


    companion object {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}