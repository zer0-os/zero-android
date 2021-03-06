package com.zero.android.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
	@PrimaryKey override val id: String,
	val name: String,
	val isNetworkAdmin: Boolean
) : BaseEntity
