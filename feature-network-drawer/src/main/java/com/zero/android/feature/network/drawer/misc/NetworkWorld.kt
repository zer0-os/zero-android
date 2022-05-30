package com.zero.android.feature.network.drawer.misc

data class NetworkWorld(
    val id: Long,
    val route: String,
    val icon: String, //expecting a url
    val title: String,
    val domain: String,
    val unreadCount: Int,
) {
    constructor() : this(123, "", "", "", "", 0)
}
