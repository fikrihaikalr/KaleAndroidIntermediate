package com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth

data class RegisterBody(
    val name: String,
    val email: String,
    val password: String,
)