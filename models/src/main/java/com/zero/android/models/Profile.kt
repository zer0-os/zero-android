package com.zero.android.models

import kotlinx.datetime.Instant

data class Profile(
    val id: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val profileImage: String? = null,
    val gender: String? = null,
    val guild: String? = null,
    val summary: String? = null,
    val skills: List<Valuable>? = null,
    val values: List<Valuable>? = null,
    val passions: List<Valuable>? = null,
    val languages: List<Valuable>? = null,
    val primaryCity: City? = null,
    val secondaryCity: City? = null,
    val hometownCity: City? = null,
    val createdAt: Instant? = null,
    val primaryEmail: String? = null,
    val secondaryEmail: String? = null,
    val primaryPhone: String? = null,
    val secondaryPhone: String? = null,
    val website: String? = null,

    // Social media
    val facebook: String? = null,
    val twitter: String? = null,
    val instagram: String? = null,
    val linkedIn: String? = null,
    val medium: String? = null,
    val github: String? = null,
    val foursquare: String? = null,
    val pinterest: String? = null,
    val behance: String? = null,
    val dribble: String? = null,
    val youtube: String? = null,
    val myspace: String? = null,
    val tumblr: String? = null,
    val flickr: String? = null,
    val wikipedia: String? = null,
    val soundcloud: String? = null,
    val spotify: String? = null,
    val appleMusic: String? = null,

    // Communication
    val wechat: String? = null,
    val signal: String? = null,
    val snapchat: String? = null,
    val skype: String? = null,
    val zoom: String? = null,
    val slack: String? = null,
    val telegram: String? = null,
    val whatsapp: String? = null,

    // Extra info
    val experiences: List<Experience>? = null,
    val investments: List<Investment>? = null,
    val educationRecords: List<Education>? = null,
    val rawAvatarURL: String? = null,
    private val _wallpaperURL: String? = null
) {

    val displayName: String
        get() = "$firstName $lastName"
}
