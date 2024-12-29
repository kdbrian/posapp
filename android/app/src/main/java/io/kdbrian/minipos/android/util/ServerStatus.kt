package io.kdbrian.minipos.android.util

import kotlinx.serialization.Serializable

@Serializable
data class ServerStatus (
    val status : String
)