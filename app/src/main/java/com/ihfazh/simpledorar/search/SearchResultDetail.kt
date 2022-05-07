package com.ihfazh.simpledorar.search

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.bookmark.BookmarkHadithBottomSheet
import com.ihfazh.simpledorar.databinding.FragmentSearchResultDetailBinding
import com.ihfazh.simpledorar.databinding.SampleDorarBinding
import id.zelory.cekrek.config.CanvasSize
import id.zelory.cekrek.config.CekrekConfig
import id.zelory.cekrek.extension.cekrekToBitmap
import id.zelory.cekrek.extension.cekrekToImageFile
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchResultDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchResultDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val args: SearchResultDetailArgs by navArgs()

    private lateinit var sampleView: SampleDorarBinding
    private lateinit var binding: FragmentSearchResultDetailBinding

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
        val resultText = SpannableString(args.resultItem.rawText)
        args.resultItem.highlights.forEach { highLight ->
            resultText.setSpan(
                ForegroundColorSpan(Color.RED),
                highLight.start,
                highLight.end + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding = FragmentSearchResultDetailBinding.inflate(inflater).apply {
            resultItem = args.resultItem
            styledText = resultText
            copy.setOnClickListener {
                val label = args.resultItem.rawText
                val text = args.resultItem.getTextForClipboard()
                val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(label, text)
                clipboardManager.setPrimaryClip(clip)
            }

            mulahadzhoh.setOnClickListener {
                val bottomSheet = BookmarkHadithBottomSheet(args.resultItem)
                bottomSheet.show(parentFragmentManager, BookmarkHadithBottomSheet.TAG)
            }

        }

        sampleView = SampleDorarBinding.inflate(inflater).apply {
            val resultItem = args.resultItem
            this.resultText.text = resultItem.rawText
            this.valueRawi.text = resultItem.rawi
            this.valueHokm.text = resultItem.hokm
            this.valueMohdits.text = resultItem.mohaddith
            this.mashdar.text = resultItem.mashdar + " " + resultItem.shafha
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.share.setOnClickListener {
            val config = CekrekConfig().apply {
                canvasConfig.width = CanvasSize.Specific(1500)
                canvasConfig.height = CanvasSize.WrapContent
            }
            val imageFile = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let{
                val file = File("${it.absolutePath}${File.separator}cekrek.jpg")
                return@let sampleView.root.cekrekToImageFile(file){
                    val canvasConfig = cekrekConfig.canvasConfig
                    canvasConfig.width = CanvasSize.Specific(1500)
                    canvasConfig.height = CanvasSize.WrapContent
                }.also {
                    Log.d("IMAGE SAVE", "onCreateView: ckerek image saved in $it")
                }
            }

            if (imageFile != null){
                val intent = Intent(Intent.ACTION_SEND).apply {
                    val uri = FileProvider.getUriForFile(requireContext(), requireActivity().packageName + ".com.ihfazh.simpledorar", imageFile)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra(Intent.EXTRA_STREAM, uri)
                    type = "image/jpg"
                }
                startActivity(Intent.createChooser(intent, "Share Image"))
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchResultDetail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchResultDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}