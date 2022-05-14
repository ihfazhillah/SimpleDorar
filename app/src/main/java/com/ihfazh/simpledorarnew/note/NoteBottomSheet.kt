package com.ihfazh.simpledorarnew.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ihfazh.simpledorarnew.databinding.NoteBottomSheetBinding
import kotlinx.android.synthetic.main.note_bottom_sheet.*

class NoteBottomSheet(private val categoryId: Long): BottomSheetDialogFragment() {
    private var binding: NoteBottomSheetBinding? = null
    private val viewModel by viewModels<NoteBottomSheetViewModel>()

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
        viewModel.setCategoryId(categoryId)

        binding = NoteBottomSheetBinding.inflate(inflater, container, false).apply {
            btnSave.setOnClickListener{
                viewModel.createOrUpdateNote()
                dismiss()
            }

            textArea.doOnTextChanged { text, start, before, count ->
                viewModel.setText(text.toString())
            }
        }

//        viewModel.text.observe(viewLifecycleOwner){
//            binding?.textArea?.setText(it)
//            Log.d(TAG, "onCreateView: text = $it")
//        }

        viewModel.note.observe(viewLifecycleOwner){
            if (it != null){
                binding?.textArea?.setText(it.text)
            }
        }
        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        val TAG: String = NoteBottomSheet::class.java.simpleName
    }
}