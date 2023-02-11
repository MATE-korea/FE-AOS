package com.mate.carpool.ui.screen.profile.lookup.item

import androidx.compose.runtime.Stable

@Stable
data class TicketState(
    val id: String,
    val startArea: String,
    val startTime: String,
    val recruitPerson: Int,
    val currentPerson: Int,
    val ticketPrice: Int,
    val driverProfileImage: String
)