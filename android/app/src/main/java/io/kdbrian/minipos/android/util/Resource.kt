package io.kdbrian.minipos.android.util

import kotlinx.serialization.Serializable

@Serializable
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val isLoading: Boolean = false
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>(isLoading = true)
}


