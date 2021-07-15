package com.brunodiaz.melichallenge.other

/*
        Funcion genérica para manejar estados
 */

data class Resource<out T>(var status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> empty(msg: String, data: T?): Resource<T> {
            return Resource(Status.EMPTY, data, msg)
        }
    }
}


enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    EMPTY
}