package com.zero.android.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zero.android.models.*
import kotlinx.datetime.Instant

@Entity(tableName = "profiles")
data class ProfileEntity(
	@PrimaryKey val id: String,
	val userId: String?,
	val fullName: String?,
	val firstName: String?,
	val lastName: String?,
	val gender: String?,
	val guild: String?,
	val summary: String?,
	@Embedded val skills: List<Valuable>?,
	@Embedded val values: List<Valuable>?,
	@Embedded val passions: List<Valuable>?,
	@Embedded val languages: List<Valuable>?,
	@Embedded val primaryCity: City?,
	@Embedded val secondaryCity: City?,
	@Embedded val hometownCity: City?,
	val createdAt: Instant?,
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
	@Embedded val experiences: List<Experience>?,
	@Embedded val investments: List<Investment>?,
	@Embedded val educationRecords: List<Education>?,
	val rawAvatarURL: String?,
	val _wallpaperURL: String?
)

fun ProfileEntity.toModel() =
	Profile(
		id = id,
		userId = userId,
		fullName = fullName,
		firstName = firstName,
		lastName = lastName,
		gender = gender,
		guild = guild,
		summary = summary,
		skills = skills,
		values = values,
		passions = passions,
		languages = languages,
		primaryCity = primaryCity,
		secondaryCity = secondaryCity,
		hometownCity = hometownCity,
		createdAt = createdAt,
		primaryEmail = primaryEmail,
		secondaryEmail = secondaryEmail,
		primaryPhone = primaryPhone,
		secondaryPhone = secondaryPhone,
		website = website,

		// Social media
		facebook = facebook,
		twitter = twitter,
		instagram = instagram,
		linkedIn = linkedIn,
		medium = medium,
		github = github,
		foursquare = foursquare,
		pinterest = pinterest,
		behance = behance,
		dribble = dribble,
		youtube = youtube,
		myspace = myspace,
		tumblr = tumblr,
		flickr = flickr,
		wikipedia = wikipedia,
		soundcloud = soundcloud,
		spotify = spotify,
		appleMusic = appleMusic,

		// Communication
		wechat = wechat,
		signal = signal,
		snapchat = snapchat,
		skype = skype,
		zoom = zoom,
		slack = slack,
		telegram = telegram,
		whatsapp = whatsapp,

		// Extra info
		experiences = experiences,
		investments = investments,
		educationRecords = educationRecords,
		rawAvatarURL = rawAvatarURL,
		_wallpaperURL = _wallpaperURL
	)
