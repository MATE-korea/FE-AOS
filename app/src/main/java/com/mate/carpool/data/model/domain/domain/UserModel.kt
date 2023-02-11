package com.mate.carpool.data.model.domain.domain

import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.ui.screen.home.item.DriverState
import com.mate.carpool.ui.screen.home.item.PassengerState

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val passWord: String,
    val profileImage: String,
    val role: MemberRole,
    val passengerId: String
) {
    fun asUserStateItem() =
        when (role) {
            MemberRole.PASSENGER -> PassengerState(
                id = id,
                name = name,
                profileImage = profileImage,
                email = email,
                passengerId = passengerId
            )
            MemberRole.DRIVER -> DriverState(
                id = id,
                name = name,
                profileImage = profileImage,
                email = email,
                carImage = "",
                phoneNumber = "",
                carNumber = ""
            )
            else -> throw IllegalStateException("IllegalStateException userState = [$this]")
        }
}