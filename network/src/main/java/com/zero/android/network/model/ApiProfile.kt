package com.zero.android.network.model

import com.zero.android.network.model.serializer.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ApiProfile(
	val id: String,
	val userId: String? = null,
	val firstName: String? = null,
	val lastName: String? = null,
	val profileImage: String? = null,
	val gender: String? = null,
	val guild: String? = null,
	val summary: String? = null,
	val skills: List<ApiValuable>? = null,
	val values: List<ApiValuable>? = null,
	val passions: List<ApiValuable>? = null,
	val languages: List<ApiValuable>? = null,
	val primaryCity: ApiCity? = null,
	val secondaryCity: ApiCity? = null,
	val hometownCity: ApiCity? = null,
	@Serializable(InstantSerializer::class) val createdAt: Instant? = null,
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
	val experiences: List<ApiExperience>? = null,
	val investments: List<ApiInvestment>? = null,
	val educationRecords: List<ApiEducation>? = null,
	val rawAvatarURL: String? = null,
	val _wallpaperURL: String? = null
)
