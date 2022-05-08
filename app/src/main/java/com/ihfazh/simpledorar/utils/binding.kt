package com.ihfazh.simpledorar.utils

import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.search.SearchState

@BindingAdapter("visibilityCondition")
fun setVisibilityByState(view: View, isVisible: Boolean){
    view.visibility = if (isVisible){
        View.VISIBLE
    } else {
        View.GONE
    }

}
