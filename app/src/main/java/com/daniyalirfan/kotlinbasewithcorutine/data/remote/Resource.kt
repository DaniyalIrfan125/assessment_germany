package com.daniyalirfan.kotlinbasewithcorutine.data.remote



data class Resource<out T>(val status: Status, val data: T?, val message: String?,  val error: Throwable? = null) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null, error: Throwable?): Resource<T> {
            return Resource(Status.ERROR, data, message,error)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}