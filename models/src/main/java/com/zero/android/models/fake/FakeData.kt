package com.zero.android.models.fake

import com.zero.android.models.Network

object FakeData {

	fun Network(id: String = "id") =
		Network(id = id, name = "display.name", displayName = "Display Name", isPublic = true)

	fun Networks() = listOf(Network("one"), Network("two"), Network("three"))
}
