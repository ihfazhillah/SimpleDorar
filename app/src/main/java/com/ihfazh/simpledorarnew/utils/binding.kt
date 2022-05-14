package com.ihfazh.simpledorarnew.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibilityCondition")
fun setVisibilityByState(view: View, isVisible: Boolean){
    view.visibility = if (isVisible){
        View.VISIBLE
    } else {
        View.GONE
    }

}
