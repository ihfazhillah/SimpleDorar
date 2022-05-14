package com.ihfazh.simpledorarnew.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.ihfazh.simpledorarnew.R
import com.ihfazh.simpledorarnew.data.LocalSearchRepository

class BookmarkCategoryCompleteAdapter(context: Context,  private val repository: LocalSearchRepository) :
    ArrayAdapter<BookmarkCategory>(context, android.R.layout.simple_list_item_1), Filterable {

    private val bookmarkCategories = mutableListOf<BookmarkCategory>()

    override fun getCount(): Int {
        return bookmarkCategories.size
    }

    override fun getItem(position: Int): BookmarkCategory? {
        return bookmarkCategories.getOrNull(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = convertView
        if (v == null){
            val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        }

        val item = bookmarkCategories[position]
        val textView = v!!.findViewById<TextView>(android.R.id.text1)
        textView.text = if (item.id == 0L) context.getString(R.string.autocomplete_not_found, item.title) else item.title

        return v
    }

    override fun getFilter(): Filter {
       return object: Filter(){
           override fun performFiltering(constraint: CharSequence?): FilterResults {
               val filterResult = FilterResults()

               if (constraint != null){
                   bookmarkCategories.clear()
                   bookmarkCategories.addAll(repository.searchCategorySync(constraint))

                   if (bookmarkCategories.size == 0){
                       bookmarkCategories.add(
                           BookmarkCategory(0, constraint as String)
                       )
                   }

                   filterResult.values = bookmarkCategories
                   filterResult.count = bookmarkCategories.size
               }

               return filterResult
           }

           override fun publishResults(constraint: CharSequence?, filterResult: FilterResults?) {
               if (filterResult != null && filterResult.count > 0){
                   notifyDataSetChanged()
               } else {
                   notifyDataSetInvalidated()
               }
           }

       }
    }

}
