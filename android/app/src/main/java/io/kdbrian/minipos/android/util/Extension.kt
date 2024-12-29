package io.kdbrian.minipos.android.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Extension {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(timestamp: Long, pattern: String): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return dateTime.format(formatter)
    }
}