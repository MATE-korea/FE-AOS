package com.mate.carpool.data.model.domain.domain

import com.mate.carpool.ui.screen.home.item.DriverState

data class DriverModel(
    val carImage: String,
    val carNumber: String,
    val phoneNumber: String,
    val profileImage: String,
    val name: String
) {
    fun asDriverState() = DriverState(
        id = "",
        name = name,
        profileImage = profileImage,
        phoneNumber = phoneNumber,
        email = "",
        carImage = carImage,
        carNumber = carNumber
    )

    companion object {
        fun getInitValue() = DriverModel(
            carImage = "",
            carNumber = "",
            phoneNumber = "",
            profileImage = "",
            name = ""
        )
    }
}