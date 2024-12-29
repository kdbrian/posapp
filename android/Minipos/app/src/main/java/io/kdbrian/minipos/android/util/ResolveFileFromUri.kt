package io.kdbrian.minipos.android.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import timber.log.Timber

object ResolveFileFromUri {
    fun Context.getFileNameFromUri(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    result = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                    Timber.d("For file $result")
                }
            }
        }
        return result
    }


    fun Context.extractBasicMetadata(uri: Uri): String {
        val metadata = StringBuilder()

        // Retrieve basic metadata via ContentResolver
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(android.provider.MediaStore.MediaColumns.DISPLAY_NAME)
            val sizeIndex = cursor.getColumnIndex(android.provider.MediaStore.MediaColumns.SIZE)
            val mimeTypeIndex = cursor.getColumnIndex(android.provider.MediaStore.MediaColumns.MIME_TYPE)

            if (cursor.moveToFirst()) {
                metadata.append("File Name: ${cursor.getString(nameIndex)}\n")
                metadata.append("File Size: ${cursor.getLong(sizeIndex)} bytes\n")
                metadata.append("Mime Type: ${cursor.getString(mimeTypeIndex)}")
            }
        }
        return metadata.toString()
    }
}