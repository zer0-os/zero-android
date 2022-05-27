package com.zero.android.feature.network.drawer.ui

/**
 * NetworkDrawerItem class for dynamic navigation drawer menu items
 */
data class ChannelNetwork(
    val route: String,
    val icon: String, //expecting a url
    val title: String,
    val domain: String,
)
