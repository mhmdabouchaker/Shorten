package com.mac.shorten.domain.data

data class DataState<out T>(
    val data: T? = null,
    val error: String? = null,
    val loading: Boolean = false,
    val empty: Boolean = false
){
    companion object{

        fun <T> success(
            data: T
        ): DataState<T>{
            return DataState(
                data = data,
            )
        }

        fun <T> error(
            message: String,
        ): DataState<T>{
            return DataState(
                error = message
            )
        }

        fun <T> loading(): DataState<T> = DataState(loading = true)

        fun <T> empty(): DataState<T> = DataState(empty = false)
    }
}
