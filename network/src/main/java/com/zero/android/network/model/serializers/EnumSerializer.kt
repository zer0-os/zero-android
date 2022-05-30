package com.zero.android.network.model.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

abstract class EnumSerializer<E : Enum<E>> : KSerializer<E> {

	final override val descriptor: SerialDescriptor =
		PrimitiveSerialDescriptor(serialName = "type", kind = PrimitiveKind.STRING)

	final override fun serialize(encoder: Encoder, value: E) = encoder.encodeString(value.name)

	final override fun deserialize(decoder: Decoder): E = decoder.decodeString().stringToEnum()

	abstract fun String?.stringToEnum(): E
}
