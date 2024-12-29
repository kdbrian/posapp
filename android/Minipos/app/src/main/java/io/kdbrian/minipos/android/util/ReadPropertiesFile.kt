package io.kdbrian.minipos.android.util

import android.content.Context
import java.io.File
import java.io.IOException
import java.util.Properties

object ReadPropertiesFile {
    fun Context.invoke(): Properties {
        val file =File(this.filesDir.parentFile?.parentFile, "local.properties").let { file ->
            if (!file.exists()) throw IOException("local.properties file not found at: ${file.absolutePath}")
            Properties().apply {
                file.inputStream().use { load(it) }
            }
        }
        return file
    }
}