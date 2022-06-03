package com.zero.android.data.conversion

import com.zero.android.database.model.ProfileEntity
import com.zero.android.models.Profile
import com.zero.android.network.model.ApiProfile

internal fun ApiProfile.toModel() =
	Profile(
		id = id,
		firstName = firstName,
		lastName = lastName,
		profileImage = profileImage,
		gender = gender,
		guild = guild,
		summary = summary,
		skills = skills?.toEntity(),
		values = values?.toEntity(),
		passions = passions?.toEntity(),
		languages = languages?.toEntity(),
		primaryCity = primaryCity?.toEntity(),
		secondaryCity = secondaryCity?.toEntity(),
		hometownCity = hometownCity?.toEntity(),
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
		experiences = experiences?.toEntity(),
		investments = investments?.toEntity(),
		educationRecords = educationRecords?.toEntity(),
		rawAvatarURL = rawAvatarURL,
		_wallpaperURL = _wallpaperURL
	)

internal fun ApiProfile.toEntity(userId: String) =
	ProfileEntity(
		id = id,
		userId = userId,
		firstName = firstName,
		lastName = lastName,
		profileImage = profileImage,
		gender = gender,
		guild = guild,
		summary = summary,
		skills = skills?.toEntity(),
		values = values?.toEntity(),
		passions = passions?.toEntity(),
		languages = languages?.toEntity(),
		primaryCity = primaryCity?.toEntity(),
		secondaryCity = secondaryCity?.toEntity(),
		hometownCity = hometownCity?.toEntity(),
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
		experiences = experiences?.toEntity(),
		investments = investments?.toEntity(),
		educationRecords = educationRecords?.toEntity(),
		rawAvatarURL = rawAvatarURL,
		_wallpaperURL = _wallpaperURL
	)
