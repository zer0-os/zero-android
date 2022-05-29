package com.zero.android.network.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ApiProfile(
	val id: String,
	val userId: String?,
	val fullName: String?,
	val firstName: String?,
	val lastName: String?,
	val gender: String?,
	val guild: String?,
	val summary: String?,
	val skills: List<ApiValuable>?,
	val values: List<ApiValuable>?,
	val passions: List<ApiValuable>?,
	val languages: List<ApiValuable>?,
	val primaryCity: ApiCity?,
	val secondaryCity: ApiCity?,
	val hometownCity: ApiCity?,
	val createdAt: LocalDateTime?,
	val primaryEmail: String?,
	val secondaryEmail: String?,
	val primaryPhone: String?,
	val secondaryPhone: String?,
	val website: String?,

	// Social media
	val facebook: String?,
	val twitter: String?,
	val instagram: String?,
	val linkedIn: String?,
	val medium: String?,
	val github: String?,
	val foursquare: String?,
	val pinterest: String?,
	val behance: String?,
	val dribble: String?,
	val youtube: String?,
	val myspace: String?,
	val tumblr: String?,
	val flickr: String?,
	val wikipedia: String?,
	val soundcloud: String?,
	val spotify: String?,
	val appleMusic: String?,

	// Communication
	val wechat: String?,
	val signal: String?,
	val snapchat: String?,
	val skype: String?,
	val zoom: String?,
	val slack: String?,
	val telegram: String?,
	val whatsapp: String?,

	// Extra info
	val experiences: List<ApiExperience>?,
	val investments: List<ApiInvestment>?,
	val educationRecords: List<ApiEducation>?,
	val rawAvatarURL: String?,
	private val _wallpaperURL: String?
) {

	val displayName: String
		get() = run {
			if (!fullName.isNullOrBlank()) {
				return fullName
			} else {
				return "$firstName $lastName"
			}
		}
}
