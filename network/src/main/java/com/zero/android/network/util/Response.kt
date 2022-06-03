package com.zero.android.network.util

import kotlinx.serialization.Serializable

@Serializable data class Response<T>(val data: T)
