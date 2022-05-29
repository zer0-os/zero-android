package com.zero.android.feature.network.drawer.ui

/**
 * NetworkWorld class for dynamic navigation drawer menu items
 */
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
