package com.zero.android.models.fake.conversationMessage

import androidx.compose.runtime.Immutable

@Immutable
data class Message(
    val author: String,
    val content: String,
    val timestamp: String,
    val image: Int? = null,
    val authorImage: String = ""
)
