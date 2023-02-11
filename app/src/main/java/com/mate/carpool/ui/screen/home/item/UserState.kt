package com.mate.carpool.ui.screen.home.item

import androidx.compose.runtime.Stable
import com.mate.carpool.data.model.item.MemberRole

@Stable
abstract class UserState {
    abstract val id: String
    abstract val name: String
    abstract val profileImage: String
    abstract val email: String
    abstract val role: MemberRole
}

@Stable
data class DriverState(
    override val id: String,
    override val name: String,
    override val profileImage: String,
    override val email: String,
    override val role: MemberRole = MemberRole.DRIVER,
    val phoneNumber: String,
    val carImage: String,
    val carNumber: String,
) : UserState() {
    companion object {
        fun getInitValue() = DriverState(
            id = "",
            name = "",
            email = "",
            phoneNumber = "",
            profileImage = "",
            carImage = "",
            carNumber = ""
        )
    }
}

@Stable
data class PassengerState(
    override val id: String,
    override val name: String,
    override val profileImage: String,
    override val email: String,
    override val role: MemberRole = MemberRole.PASSENGER,
    val passengerId: String,
) : UserState() {
    companion object {
        fun getInitValue() = PassengerState(
            id = "",
            name = "",
            email = "",
            profileImage = "",
            passengerId = ""
        )
    }
}