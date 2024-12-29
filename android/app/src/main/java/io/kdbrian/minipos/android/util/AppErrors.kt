package io.kdbrian.minipos.android.util

import kotlinx.serialization.Serializable
import timber.log.Timber

@Serializable
sealed class AppErrors(
    override val message: String?
) : Exception(message) {
    companion object {
        fun handleErrors(e: Exception): Result<Nothing> {
            Timber.d("Failed $e")
            Timber.d("Failed ${e.message}")
            return when (e) {
                is AppErrors -> Result.failure(e)
                is IllegalArgumentException -> {
                    if (e.message!!.contains("URL scheme")) {
                        Result.failure(Throwable(message = "Set host Url in properties & rebuild."))
                    }else
                        Result.failure(e)
                }
                else -> Result.failure(e)
            }
        }
    }
}