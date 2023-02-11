package com.mate.carpool.ui.navigation

import androidx.compose.runtime.Stable

@Stable
sealed class NavigationItem(
    val title:String,
    val route:String
) {
    object Home: NavigationItem(
        title = "홈",
        route = "home/{event}"
    )
    object Announcement: NavigationItem(
        title = "공지",
        route = "announcement"
    )
    object Report: NavigationItem(
        title = "신고",
        route = "report/{ticketId}/{userId}"
    )

    sealed class RegisterDriver(val step:String) : NavigationItem(title = "드라이버 등록", route = "registerDriver/$step"){
        object StepCarImage : RegisterDriver(step = "carImage")
        object StepCarNumber : RegisterDriver(step = "carNumber")
        object StepPhoneNumber : RegisterDriver(step = "phoneNumber")
    }

    object TicketUpdate: NavigationItem(
        title = "티켓수정",
        route = "ticketUpdate"
    )
}