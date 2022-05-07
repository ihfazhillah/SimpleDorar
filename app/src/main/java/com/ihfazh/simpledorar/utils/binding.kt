package com.ihfazh.simpledorar.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.search.SearchState

@BindingAdapter("visibilityCondition")
fun setVisibilityByState(view: View, state: SearchState){
    val visibilityMap = mapOf(
        SearchState.HasHistory to R.id.search_history
    )

    val shouldVisibleId =  visibilityMap[state]
    view.visibility = if (shouldVisibleId == view.id){
        View.VISIBLE
    } else {
        View.INVISIBLE
    }

}
