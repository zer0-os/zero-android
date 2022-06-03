package com.zero.android.database.converter

import androidx.room.TypeConverter
import com.zero.android.models.MessageType
import com.zero.android.models.toMessageType

class EnumConverters {

	@TypeConverter
	fun messageTypeToString(value: MessageType?): String? = value?.let(MessageType::serializedName)

	@TypeConverter
	fun stringToMessageType(serializedName: String?): MessageType = serializedName.toMessageType()
}
