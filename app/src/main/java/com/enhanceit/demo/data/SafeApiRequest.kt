package com.enhanceit.demo.data

import com.enhanceit.demo.utils.APIException
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T? {
        val response: Response<T>? = call.invoke()

        if (response?.isSuccessful == true) {
            return response?.body()
        } else {

            throw (APIException(
                response?.errorBody().toString()
            ))
        }
    }
}