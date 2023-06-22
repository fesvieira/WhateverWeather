package com.fesvieira.whateverweather.models

import retrofit2.Response

abstract class BaseRepository {
    protected suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call.invoke()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.Success(body)
            } else {
                val string = response.errorBody()?.string().toString()
                Result.Error(string)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Internet error.")
        }
    }
}