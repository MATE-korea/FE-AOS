package com.mate.carpool.data.model.dto.dto.response

import com.google.gson.annotations.SerializedName
import com.mate.carpool.data.model.domain.domain.DriverModel

data class DriverDTO(
    @SerializedName("carImageUrl") val carImage: String,
    @SerializedName("carNumber") val carNumber: String,
    @SerializedName("name") val name: String?,
    @SerializedName("profileImageUrl") val profileImage: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?,
) {
    fun asDriverDomainModel() = DriverModel(
        carImage = carImage,
        carNumber = carNumber,
        phoneNumber = phoneNumber ?: "",
        profileImage = profileImage ?: "",
        name = name ?: ""
    )
}