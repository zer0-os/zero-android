package com.zero.android.database.model

import androidx.room.*
import com.zero.android.models.*
import kotlinx.datetime.Instant

@Entity(
	tableName = "profiles",
	foreignKeys =
	[
		ForeignKey(
			entity = ProfileEntity::class,
			parentColumns = ["id"],
			childColumns = ["userId"],
			onDelete = ForeignKey.CASCADE
		)
	],
	indices = [Index(value = ["userId"])]
)
data class ProfileEntity(
	@PrimaryKey val id: String,
	val userId: String?,
	val firstName: String?,
	val lastName: String?,
	val profileImage: String?,
	val gender: String?,
	val guild: String?,
	val summary: String?,
	val skills: List<Valuable>?,
	val values: List<Valuable>?,
	val passions: List<Valuable>?,
	val languages: List<Valuable>?,
	@Embedded(prefix = "primary_city_") val primaryCity: City?,
	@Embedded(prefix = "secondary_city_") val secondaryCity: City?,
	@Embedded(prefix = "home_city_") val hometownCity: City?,
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
	val experiences: List<Experience>?,
	val investments: List<Investment>?,
	val educationRecords: List<Education>?,
	val rawAvatarURL: String?,
	val _wallpaperURL: String?
)

fun ProfileEntity.toModel() =
	Profile(
		id = id,
		firstName = firstName,
		lastName = lastName,
		profileImage = profileImage,
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
