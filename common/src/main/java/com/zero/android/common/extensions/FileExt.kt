package com.zero.android.common.extensions

import android.content.Context
import android.net.Uri
import java.io.File

fun Uri.toFile(context: Context): File {
	val inputStream = context.contentResolver.openInputStream(this)
	val tempFile =
		File.createTempFile("temp_${System.currentTimeMillis().div(1000)}", "", context.cacheDir)
	tempFile.outputStream().use { inputStream?.copyTo(it) }
	return tempFile
}
