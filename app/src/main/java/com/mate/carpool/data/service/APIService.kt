package com.mate.carpool.data.service

import com.mate.carpool.data.model.*
import com.mate.carpool.data.model.dto.*
import com.mate.carpool.data.model.item.StudentItem
import com.mate.carpool.data.model.dto.ProfileDto
import com.mate.carpool.data.model.dto.TicketDeleteMemberRequestDTO
import com.mate.carpool.data.model.dto.dto.request.CreateTicketDTO
import com.mate.carpool.data.model.dto.dto.request.DriverRegisterDTO
import com.mate.carpool.data.model.dto.dto.request.LoginDTO
import com.mate.carpool.data.model.dto.dto.request.SignUpDTO
import com.mate.carpool.data.model.dto.dto.response.CommonResponse
import com.mate.carpool.data.model.dto.dto.response.ProfileDTO
import com.mate.carpool.data.model.dto.dto.response.TicketListDTO
import com.mate.carpool.data.model.dto.request.ReportRequest
import com.mate.carpool.data.model.dto.request.UpdateMyProfileRequest
import com.mate.carpool.data.model.response.LoginResponse
import com.mate.carpool.data.model.response.ResponseMessage
import okhttp3.MultipartBody
import retrofit2.http.*

interface APIService {

    @POST("auth/login")
    suspend fun login(@Body loginDTO: LoginDTO): LoginResponse

    @POST("auth/logout")
    suspend fun logout(): CommonResponse

    @POST("auth/signup")
    suspend fun signUp(signUpDTO: SignUpDTO): CommonResponse

    @Multipart
    @POST("driver")
    suspend fun registerDriver(
        @Part image:MultipartBody.Part,
        @Part driverRegisterDTO: DriverRegisterDTO
    ): CommonResponse

    @POST("carpool")
    suspend fun createTicket(createTicketRequestDTO: CreateTicketDTO): CommonResponse

    @GET("carpool")
    suspend fun getTicketList(): List<TicketListDTO>

    @GET("member")
    suspend fun getMyProfileNew(): ProfileDTO




    /**
     * refactor
     */
    @GET("auth/test")
    suspend fun checkAccessTokenIsExpired(): String

    @POST("auth/login")
    suspend fun postLogin(@Body studentInfo: StudentItem): LoginResponse


    @GET("member/check/class/{studentNumber}")
    suspend fun checkIsStudentNumberExists(
        @Path("studentNumber") studentNumber: String
    ): ResponseMessage

    @GET("ticket/read/{id}")
    suspend fun getTicketReadId(@Path("id") id: Long): TicketDetailResponseDTO

    @GET("ticket/update/{id}")
    suspend fun getTicketUpdateId(
        @Path("id") id: Long,
        @Query("status") status: String
    ): ResponseMessage

    //@GET("ticket/list")
    //suspend fun getTicketList(): List<UserTicketDTO>

    @GET("ticket/promise")
    suspend fun getMyTicket(): TicketDetailResponseDTO

    @GET("member/me")
    suspend fun getMemberMe(): MemberProfileDTO

    @POST("passenger/new")
    suspend fun postPassengerNew(@Body ticketId: TicketNewMemberRequestDTO): ResponseMessage

    @HTTP(method = "DELETE", path = "passenger", hasBody = true)
    suspend fun deletePassenger(@Body ticket: TicketDeleteMemberRequestDTO): ResponseMessage

    @GET("member/me")
    suspend fun getMyProfile(): ProfileDto

    @PUT("member/update/profile")
    suspend fun updateProfile(@Body body: UpdateMyProfileRequest): ResponseMessage

    @Multipart
    @PUT("member/update/image")
    suspend fun updateProfileImage(@Part body: MultipartBody.Part?): ResponseMessage

    @POST("report/new")
    suspend fun report(@Body body: ReportRequest): ResponseMessage
}
