package com.zero.android.models

data class Task(
	val id: String,
	val latitude: Double?,
	val longitude: Double?,
	val name: String?,
	val description: String?,
	val coverPhotoURL: String?,
	val location: String?,
	val walletId: String?,
	val discussionId: String?,
	val fileKey: String?,
	val amount: Double?,
	val geofenceRadiusMeters: Int?,
	val currency: Currency?,
	val assignments: List<User>?,
	val wallet: Wallet?,
	private val editPermission: Boolean,
	val geoLockRead: Boolean
) {

	private fun isGeoTask() = latitude != null && longitude != null

	// we are outside of the geofence. This actually works now but it can be misleading if we
	// were to make an API call that did not include the task description in the response
	// regardless of geo-fence setting
	fun isOutsideGeofence() = isGeoTask() && !geoLockRead

	fun isInsideGeofence() = isGeoTask() && geoLockRead

	fun canEdit() = editPermission

	// For now, if they have edit permission, current user is a creator
	fun isCreator() = canEdit()

	fun hasAugmentedRealityObject(): Boolean {
		val key = fileKey ?: return false
		val normalized = key.split(".").last().lowercase()
		return normalized == "obj" || normalized == "scn" || normalized == "dae"
	}
}
