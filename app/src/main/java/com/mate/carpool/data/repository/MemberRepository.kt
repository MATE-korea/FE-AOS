package com.mate.carpool.data.repository

import com.mate.carpool.data.Result
import com.mate.carpool.data.model.domain.domain.DriverModel
import com.mate.carpool.data.model.domain.domain.UserModel
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.time.DayOfWeek

interface MemberRepository {
    fun updateProfile(
        phone: String,
        userRole: MemberRole,
        daysOfUse: List<DayOfWeek>
    ): Flow<Result<ResponseMessage>>

    fun updateProfileImage(part: MultipartBody.Part): Flow<Result<ResponseMessage>>
    fun getMyProfile(): Flow<UserModel>
    fun getDriverProfile(): Flow<DriverModel>
}

