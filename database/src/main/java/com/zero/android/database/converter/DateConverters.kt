package com.zero.android.database.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class DateConverters {

	@TypeConverter
	fun longToInstant(value: Long?): Instant? = value?.let(Instant::fromEpochMilliseconds)

	@TypeConverter fun instantToLong(instant: Instant?): Long? = instant?.toEpochMilliseconds()
}
