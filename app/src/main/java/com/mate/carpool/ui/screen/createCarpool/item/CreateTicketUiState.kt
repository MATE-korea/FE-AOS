package com.mate.carpool.ui.screen.createCarpool.item

import androidx.compose.runtime.Stable

@Stable
data class CreateTicketUiState(
    val driverName: String,
    val driverProfile: String,
    val startArea: String,
    val boardingPlace: String,
    val endArea: String,
    val startTime: String,
    val openChatLink: String,
    val recruitNumber: String,
    val fee: String,
    val invalidArea: Boolean,
    val invalidStartTime: Boolean,
    val invalidOpenChatLink: Boolean,
    val invalidRecruitNumber: Boolean,
    val invalidFee: Boolean
) {

    companion object {
        fun getInitValue() = CreateTicketUiState(
            driverName = "홍길동",
            driverProfile = "",
            startArea = "",
            boardingPlace = "",
            endArea = "",
            startTime = "",
            openChatLink = "",
            recruitNumber = "",
            fee = "",
            invalidArea = false,
            invalidStartTime = false,
            invalidOpenChatLink = false,
            invalidRecruitNumber = false,
            invalidFee = false
        )
    }
}