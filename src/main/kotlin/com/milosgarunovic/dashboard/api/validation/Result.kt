package com.milosgarunovic.dashboard.api.validation

/**
 * Used as an result of a success/failure for a request on core and resource levels. Based on
 * https://medium.com/swlh/kotlin-sealed-class-for-success-and-error-handling-d3054bef0d4e
 */
sealed class Result<out T> {
    data class Success<out R>(val value: R) : Result<R>()
    data class Failure(val message: String?) : Result<Nothing>()
}

inline fun <reified T> Result<T>.onSuccess(block: (value: T) -> Unit): Result<T> {
    if (this is Result.Success) {
        block(value)
    }
    return this
}

inline fun <T> Result<T>.onFailure(block: (error: String?) -> Unit): Result<T> {
    if (this is Result.Failure) {
        block(message)
    }
    return this
}

fun <T> Result<T>.isSuccess(): Boolean = this is Result.Success

fun <T> Result<T>.isFailure(): Boolean = this is Result.Failure