package com.zero.android.network.model

class ApiProfile(
	val id: String,
	val userId: String?,
	val fullName: String?,
	val firstName: String?,
	val lastName: String?,
	val gender: String?,
	val guild: String?,
	val summary: String?,
	val skills: List<Skill>?,
	val values: List<Value>?,
	val passions: List<Passion>?,
	val languages: List<Language>?,
	val primaryCity: City?,
	val secondaryCity: City?,
	val hometownCity: City?,
	val createdAt: Date?,
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
	val experiences: List<Experience>?,
	val investments: List<Investment>?,
	val educationRecords: List<Education>?,
	val rawAvatarURL: URL?,
	private val _wallpaperURL: URL?
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
