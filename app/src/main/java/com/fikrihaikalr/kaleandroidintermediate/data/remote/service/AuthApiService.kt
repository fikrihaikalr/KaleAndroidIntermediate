package com.fikrihaikalr.kaleandroidintermediate.data.remote.service

import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.LoginBody
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.LoginResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.RegisterBody
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST(LOGIN)
    suspend fun login(@Body loginBody: LoginBody): LoginResponse

    @POST(REGISTER)
    suspend fun register(@Body registerBody: RegisterBody): RegisterResponse

    companion object {
        const val LOGIN = "login"
        const val REGISTER = "register"
    }

}