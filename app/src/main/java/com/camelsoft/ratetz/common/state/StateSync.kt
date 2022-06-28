package com.camelsoft.ratetz.common.state

sealed class StateSync<T> {
    class Success<T>(val data: T) : StateSync<T>()
    class Error<T>(val message: String) : StateSync<T>()
}