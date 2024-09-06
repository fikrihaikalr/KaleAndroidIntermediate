package com.fikrihaikalr.kaleandroidintermediate.data.remote.datasource

import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.LoginBody
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.LoginResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.RegisterBody
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.RegisterResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.service.AuthApiService
import javax.inject.Inject

interface AuthRemoteDataSource {
    suspend fun login(loginBody: LoginBody): LoginResponse
    suspend fun register(registerBody: RegisterBody): RegisterResponse
}

class AuthRemoteDataSourceImpl @Inject constructor(private val authApiService: AuthApiService) :
    AuthRemoteDataSource {
    override suspend fun login(loginBody: LoginBody): LoginResponse =
        authApiService.login(loginBody)

    override suspend fun register(registerBody: RegisterBody): RegisterResponse =
        authApiService.register(registerBody)
}