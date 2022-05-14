package com.ihfazh.simpledorar

import android.graphics.drawable.Drawable
import android.view.View

data class GeneralItem(
    val id: String,
    val title: String,
    val subtitle: String?,
    val image: Drawable?,
    val action: (View) -> Unit = {},
)
