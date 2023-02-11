package com.mate.carpool.data.repository.impl

import com.mate.carpool.data.Result
import com.mate.carpool.data.callApi
import com.mate.carpool.data.model.domain.domain.DriverModel
import com.mate.carpool.data.model.domain.domain.UserModel
import com.mate.carpool.data.model.dto.request.UpdateMyProfileRequest
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.MemberRepository
import com.mate.carpool.data.service.APIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import java.time.DayOfWeek
import javax.inject.Inject


class MemberRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : MemberRepository {

    override fun getMyProfile(): Flow<UserModel> = flow {
        emit(apiService.getMyProfile())
    }.map { profileDTO ->
        profileDTO.asUserDomainModel()
    }

    override fun getDriverProfile(): Flow<DriverModel> = flow {
        emit(apiService.getDriverProfile())
    }.map { driverDTO ->
        driverDTO.asDriverDomainModel()
    }

    override fun updateProfile(
        phone: String,
        userRole: MemberRole,
        daysOfUse: List<DayOfWeek>
    ): Flow<Result<ResponseMessage>> = callApi {
        val body = UpdateMyProfileRequest.fromDomain(
            phone = phone,
            userRole = userRole,
            daysOfUse = daysOfUse
        )
        apiService.updateProfile(body)
    }

    override fun updateProfileImage(part: MultipartBody.Part) = callApi {
        apiService.updateProfileImage(body = part)
    }
}