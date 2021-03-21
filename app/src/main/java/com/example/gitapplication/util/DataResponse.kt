package com.example.gitapplication.util


class DataResponse<T>(val status: Status, val data: T?, val error: Throwable?) {
    companion object {
        fun <T> loading(): DataResponse<T> = DataResponse(Status.LOADING, null, null)

        fun <T> success(data: T?): DataResponse<T> = DataResponse(Status.SUCCESS, data, null)

        fun <T> error(throwable: Throwable?): DataResponse<T> = DataResponse(Status.ERROR, null, throwable)

        fun <T> complete(): DataResponse<T> = DataResponse(Status.COMPLETE, null, null)
    }
    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
        COMPLETE
    }
}
