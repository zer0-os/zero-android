package com.zero.android.common.extensions

import android.content.Context
import android.net.Uri
import android.os.Build
import java.io.File
import java.io.FileOutputStream
import java.net.URL

fun Uri.toFile(context: Context): File {
	val inputStream = context.contentResolver.openInputStream(this)
	val tempFile =
		File.createTempFile("temp_${System.currentTimeMillis().div(1000)}", "", context.cacheDir)
	tempFile.outputStream().use { inputStream?.copyTo(it) }
	return tempFile
}

fun URL.downloadFile(path: String) {
	this.openStream().use { input ->
		FileOutputStream(File(path)).use { output -> input.copyTo(output) }
	}
}

fun URL.downloadFile(path: String, progress: ((Long, Long) -> Unit)) {
	val connection = this.openConnection()
	connection.connect()
	val length =
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) connection.contentLengthLong
		else connection.contentLength.toLong()
	this.openStream().use { input ->
		FileOutputStream(File(path)).use { output ->
			val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
			var bytesRead = input.read(buffer)
			var bytesCopied = 0L
			while (bytesRead >= 0) {
				output.write(buffer, 0, bytesRead)
				bytesCopied += bytesRead
				progress.invoke(bytesCopied, length)
				bytesRead = input.read(buffer)
			}
		}
	}
}
