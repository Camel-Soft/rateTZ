package com.camelsoft.ratetz.common.state

sealed class StateAsync<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : StateAsync<T>(data)
    class Error<T>(message: String, data: T? = null) : StateAsync<T>(data, message)
    class Loading<T>(data: T? = null) : StateAsync<T>(data)
}