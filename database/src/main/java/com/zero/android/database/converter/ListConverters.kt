package com.zero.android.database.converter

import androidx.room.TypeConverter
import com.zero.android.models.Education
import com.zero.android.models.Experience
import com.zero.android.models.FileThumbnail
import com.zero.android.models.Investment
import com.zero.android.models.MessageReaction
import com.zero.android.models.Valuable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListConverters {

	@TypeConverter
	fun stringToValuableList(value: String?): List<Valuable>? =
		value?.let { Json.decodeFromString<List<Valuable>>(value) }

	@TypeConverter
	fun valuableListToString(value: List<Valuable>?): String? =
		value?.let { Json.encodeToString(value) }

	@TypeConverter
	fun stringToEducationList(value: String?): List<Education>? =
		value?.let { Json.decodeFromString<List<Education>>(value) }

	@TypeConverter
	fun educationListToString(value: List<Education>?): String? =
		value?.let { Json.encodeToString(value) }

	@TypeConverter
	fun stringToInvestmentList(value: String?): List<Investment>? =
		value?.let { Json.decodeFromString<List<Investment>>(value) }

	@TypeConverter
	fun investmentListToString(value: List<Investment>?): String? =
		value?.let { Json.encodeToString(value) }

	@TypeConverter
	fun stringToExperienceList(value: String?): List<Experience>? =
		value?.let { Json.decodeFromString<List<Experience>>(value) }

	@TypeConverter
	fun experienceListToString(value: List<Experience>?): String? =
		value?.let { Json.encodeToString(value) }

	@TypeConverter
	fun stringToStringList(value: String?): List<String>? =
		value?.let { Json.decodeFromString<List<String>>(value) }

	@TypeConverter
	fun stringListToString(value: List<String>?): String? = value?.let { Json.encodeToString(value) }

	@TypeConverter
	fun stringToStringMap(value: String?): Map<String, String?>? =
		value?.let { Json.decodeFromString<Map<String, String?>>(value) }

	@TypeConverter
	fun stringMapToString(value: Map<String, String?>?): String? =
		value?.let { Json.encodeToString(value) }

	@TypeConverter
	fun stringToMessageReactionList(value: String?): List<MessageReaction>? =
		value?.let { Json.decodeFromString<List<MessageReaction>>(value) }

	@TypeConverter
	fun messageReactionListToString(value: List<MessageReaction>?): String? =
		value?.let { Json.encodeToString(value) }

	@TypeConverter
	fun stringToFileThumbnailList(value: String?): List<FileThumbnail>? =
		value?.let { Json.decodeFromString<List<FileThumbnail>>(value) }

	@TypeConverter
	fun fileThumbnailListToString(value: List<FileThumbnail>?): String? =
		value?.let { Json.encodeToString(value) }
}
