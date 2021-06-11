package com.android.imagesearch.network

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(
                status = Status.SUCCESS,
                data = data,
                message = null
            )

        fun <T> error(message: String, data: T? = null): Resource<T> =
            Resource(
                status = Status.ERROR,
                data = data,
                message = message
            )
    }
}