package io.kdbrian.minipos.android.util

object DatePatterns {
    const val SHORT_DATE = "dd/MM/yyyy" // e.g., 28/12/2024
    const val FULL_DATE = "EEEE, dd MMMM yyyy" // e.g., Saturday, 28 December 2024
    const val DATE_WITH_MONTH_NAME = "dd MMM yyyy" // e.g., 28 Dec 2024
    const val MONTH_AND_YEAR = "MMMM yyyy" // e.g., December 2024
    const val ISO_DATE = "yyyy-MM-dd" // e.g., 2024-12-28

    // Time patterns
    const val TIME_24_HOUR = "HH:mm:ss" // e.g., 14:35:50
    const val TIME_12_HOUR = "hh:mm:ss a" // e.g., 02:35:50 PM
    const val SHORT_TIME = "h:mm a" // e.g., 2:35 PM

    // Combined Date and Time patterns
    const val FULL_DATE_TIME_24_HOUR = "yyyy-MM-dd HH:mm:ss" // e.g., 2024-12-28 14:35:50
    const val FULL_DATE_TIME_12_HOUR = "yyyy-MM-dd hh:mm:ss a" // e.g., 2024-12-28 02:35:50 PM
    const val DATE_WITH_TIME = "EEEE, dd MMM yyyy, HH:mm" // e.g., Saturday, 28 Dec 2024, 14:35
    const val READABLE_DATE_TIME = "EEE, MMM d, yyyy 'at' h:mm a" // e.g., Sat, Dec 28, 2024 at 2:35 PM

}