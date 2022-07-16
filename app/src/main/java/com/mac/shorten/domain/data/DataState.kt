package com.mac.shorten.domain.data

data class DataState<out T>(
    val status: Status,
    val data: T? = null,
    val error: String? = null
){

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        EMPTY
    }

    companion object{

        fun <T> success(
            data: T
        ): DataState<T>{
            return DataState(
                status = Status.SUCCESS,
                data = data
            )
        }

        fun <T> error(
            message: String,
        ): DataState<T>{
            return DataState(
                status = Status.ERROR,
                error = message
            )
        }


        fun <T> loading(): DataState<T> = DataState(Status.LOADING)

        fun <T> empty(): DataState<T> = DataState(Status.EMPTY)
    }
}
