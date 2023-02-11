package com.mate.carpool.data.service

import com.mate.carpool.data.model.*
import com.mate.carpool.data.model.dto.*
import com.mate.carpool.data.model.dto.dto.request.*
import com.mate.carpool.data.model.dto.dto.response.*
import com.mate.carpool.data.model.dto.dto.request.ReportRequest
import com.mate.carpool.data.model.dto.request.UpdateMyProfileRequest
import com.mate.carpool.data.model.response.ResponseMessage
import okhttp3.MultipartBody
import retrofit2.http.*

interface APIService {

    @POST("auth/login")
    suspend fun login(@Body loginDTO: LoginDTO): LoginResponse

    @POST("auth/logout")
    suspend fun logout(): CommonResponse

    @POST("auth/reissue")
    suspend fun reNewAccessToken(@Body reissueDTO: ReissueDTO): LoginResponse

    @POST("auth/signup")
    suspend fun signUp(signUpDTO: SignUpDTO): CommonResponse

    @POST("auth/withdrawal")
    suspend fun withDraw(): CommonResponse

    @Multipart
    @POST("driver")
    suspend fun registerDriver(
        @Part image: MultipartBody.Part,
        @Part driverRegisterDTO: DriverRegisterDTO
    ): CommonResponse

    @POST("carpool")
    suspend fun createTicket(createTicketRequestDTO: CreateTicketDTO): CommonResponse

    @GET("carpool")
    suspend fun getTicketList(): List<TicketListDTO>

    @GET("member")
    suspend fun getMyProfile(): ProfileDTO

    @GET("driver/{memberId}")
    suspend fun getDriverProfile(): DriverDTO

    @GET("carpool/promise")
    suspend fun getMyTicket(): TicketDetailDTO

    @GET("carpool/{carpool_id}")
    suspend fun getTicketById(@Path("carpool_id") id: String): TicketDetailDTO // currentPerson 이 없는데 되는지 확인해봐야함

    @POST("passenger/ride/{carpool_id}")
    suspend fun addPassengerToTicket(@Path("carpool_id") id: String): CommonResponse

    @PUT("carpool")
    suspend fun updateTicketDetail(@Body updateTicketDTO: UpdateTicketDTO): CommonResponse

    @HTTP(method = "DELETE", path = "passenger/{passenger_id}", hasBody = false)
    suspend fun deleteUserFromTicket(@Path("passenger_id") id: String): CommonResponse

    @HTTP(method = "DELETE", path = "carpool/{carpool_id}", hasBody = false)
    suspend fun deleteMyTicket(@Path("carpool_id") id: String): CommonResponse

    @POST("report")
    suspend fun report(@Body reportRequest: ReportRequest): CommonResponse

    /**
     * refactor
     */

    @PUT("member/update/profile")
    suspend fun updateProfile(@Body body: UpdateMyProfileRequest): ResponseMessage

    @Multipart
    @PUT("member/update/image")
    suspend fun updateProfileImage(@Part body: MultipartBody.Part?): ResponseMessage
}
