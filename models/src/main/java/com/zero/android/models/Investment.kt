package com.zero.android.models

import kotlinx.datetime.Instant

data class Investment(
	val round: String?,
	val investmentDate: Instant?,
	val amount: String?,
	val description: String?,
	val organization: Organization?
)
