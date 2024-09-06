package com.fikrihaikalr.kaleandroidintermediate.data.repository

import com.fikrihaikalr.kaleandroidintermediate.data.local.datasource.AuthLocalDataSource
import com.fikrihaikalr.kaleandroidintermediate.data.remote.datasource.AuthRemoteDataSource
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.LoginBody
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.LoginResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.RegisterBody
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.RegisterResponse
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import com.fikrihaikalr.kaleandroidintermediate.wrapper.proceed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthRepository {
    fun getToken(): Flow<String>
    suspend fun setToken(token:String)
    suspend fun clear()
    suspend fun login(loginBody: LoginBody): Resource<LoginResponse>
    suspend fun register(registerBody: RegisterBody): Resource<RegisterResponse>
}

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override fun getToken(): Flow<String> = authLocalDataSource.getToken()

    override suspend fun setToken(token: String) = authLocalDataSource.setToken(token)

    override suspend fun clear() = authLocalDataSource.clearToken()

    override suspend fun login(loginBody: LoginBody): Resource<LoginResponse> {
        val response = proceed { authRemoteDataSource.login(loginBody) }
        response.payload?.loginResult?.token?.let { authLocalDataSource.setToken(it) }
        return response
    }

    override suspend fun register(registerBody: RegisterBody): Resource<RegisterResponse> =
        proceed {
            authRemoteDataSource.register(registerBody)
        }
}