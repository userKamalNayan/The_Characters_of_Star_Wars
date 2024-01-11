package com.kamalnayan.data.error

import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import org.json.JSONException
import org.json.JSONObject

data class ErrorEnvelope(
    val apiErrorMessage: String?,
)

object ErrorEnvelopeMapper : ApiErrorModelMapper<ErrorEnvelope> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ErrorEnvelope {
        val apiErrorMessage = try {
            apiErrorResponse.errorBody?.string()?.let {
                JSONObject(it).optString("detail")
            }
        } catch (e: JSONException) {
            null
        }
        return ErrorEnvelope(apiErrorMessage)
    }
}