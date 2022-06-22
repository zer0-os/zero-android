package com.zero.android.ui.home

import androidx.annotation.DrawableRes

internal data class HomeTab(
    val id: Long,
    @DrawableRes val icon: Int,
    val title: String,
    val count: Int
)
