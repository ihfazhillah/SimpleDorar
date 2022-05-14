package com.ihfazh.simpledorarnew

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.simpledorarnew.databinding.FragmentInfoBinding
import com.ihfazh.simpledorarnew.databinding.GeneralItemListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GeneralInfoAdapter(private val items: List<GeneralItem>):
RecyclerView.Adapter<GeneralInfoAdapter.ViewHolder>(){
    class ViewHolder(private val binding: GeneralItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GeneralItem) {
            binding.apply {
                root.setOnClickListener { item.action(it) }
                setItem(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GeneralItemListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentInfoBinding? = null

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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf(
            GeneralItem(
                "version",
                "App Version",
                BuildConfig.VERSION_NAME,
                requireContext().getDrawable(R.drawable.ic_baseline_info_24)
            ),
            GeneralItem(
                "api_source",
                "Search Source",
                "dorar.net API",
                requireContext().getDrawable(R.drawable.ic_baseline_menu_book_24),
            ),
            GeneralItem(
                "manhajiyyah",
                "Takhrij Methodology",
                "Work methodology in the dorar.net",
                requireContext().getDrawable(R.drawable.ic_baseline_search_24),
                action = { openManhajiyya() }
            ),
            GeneralItem(
                "send_email",
                "Contact Me",
                "me@ihfazh.com",
                requireContext().getDrawable(R.drawable.ic_baseline_email_24),
                action = { sendEmail() }
            ),
            GeneralItem(
                "Licences",
                "Opensource Licences",
                null,
                requireContext().getDrawable(R.drawable.ic_baseline_code_24),
                action = { openLicences() }
            ),
            GeneralItem(
                "rate",
                "Rate App",
                null,
                requireContext().getDrawable(R.drawable.ic_baseline_star_rate_24),
                action = { openRate() }
            ),
        )

        binding?.apply {
           info.adapter = GeneralInfoAdapter(items)
           info.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun openManhajiyya() {
        val url = "https://dorar.net/article/77/%D8%A7%D9%84%D9%85%D9%88%D8%B3%D9%88%D8%B9%D8%A9-%D8%A7%D9%84%D8%AD%D8%AF%D9%8A%D8%AB%D9%8A%D8%A9-%D8%B9%D9%85%D9%84%D9%86%D8%A7-%D9%81%D9%8A-%D8%A7%D9%84%D9%85%D9%88%D8%B3%D9%88%D8%B9%D8%A9"
        val intent = Intent(Intent.ACTION_VIEW).apply{
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    private fun openLicences() {
        val action = NavGraphDirections.goToLicense()
        findNavController().navigate(action)
    }

    private fun openRate(){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://play.google.com/store/apps/details?id=com.ihfazh.simpledorarnew")
            setPackage("com.android.vending")
        }
        startActivity(intent)
    }

    private fun sendEmail(){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("me@ihfazh.com"))
            putExtra(Intent.EXTRA_SUBJECT, "SimpleDorar: ")
        }
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}