package com.mate.carpool.data.repository

import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.Result
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val autoLoginInfo:Flow<AutoLoginPreferences>
    fun login(email: String, password: String): Flow<Result<ResponseMessage>>
    fun logout(): Flow<Result<ResponseMessage>>
    fun signUp(name: String, email: String, password: String): Flow<Result<ResponseMessage>>
    fun temporaryLogin(): Flow<Result<ResponseMessage>>
    fun checkAccessTokenIsExpired(): Flow<Result<ResponseMessage>>
}