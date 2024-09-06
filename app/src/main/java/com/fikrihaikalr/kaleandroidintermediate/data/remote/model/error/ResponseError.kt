package com.fikrihaikalr.kaleandroidintermediate.data.remote.model.error

import com.google.gson.annotations.SerializedName



data class ResponseError(
    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
