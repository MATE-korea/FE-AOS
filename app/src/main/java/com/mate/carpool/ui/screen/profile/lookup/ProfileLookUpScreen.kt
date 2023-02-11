package com.mate.carpool.ui.screen.profile.lookup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.domain.StartArea
import com.mate.carpool.data.model.domain.Ticket
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.ui.composable.HorizontalDividerItem
import com.mate.carpool.ui.screen.profile.lookup.component.HistoryGroup
import com.mate.carpool.ui.screen.profile.lookup.component.HistoryHeaderItem
import com.mate.carpool.ui.screen.profile.lookup.component.TopAppBar
import com.mate.carpool.ui.screen.profile.lookup.component.UserBottomInfoItem
import com.mate.carpool.ui.screen.profile.lookup.component.UserTopInfoItem
import com.mate.carpool.data.model.domain.Profile
import com.mate.carpool.ui.theme.primary10
import com.mate.carpool.ui.theme.white
import com.mate.carpool.ui.util.date
import com.mate.carpool.ui.util.month
import com.mate.carpool.ui.util.year
import com.mate.carpool.util.MatePreview
import okhttp3.MultipartBody
import java.time.DayOfWeek
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLookUpScreen(
    profile: Profile?,
    setProfileImage: (MultipartBody.Part) -> Unit,
    onEditClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val groups by remember(profile?.recentTickets) {
        derivedStateOf {
            profile?.recentTickets?.groupBy { ticket ->
                val cal = Calendar.getInstance()
                cal.timeInMillis = ticket.startTime
                Triple(cal.year, cal.month, cal.date)
            }?.toList() ?: emptyList()
        }
    }

    Scaffold(
        containerColor = white,
        topBar = {
            TopAppBar(
                name = profile?.name ?: "",
                onEditClick = onEditClick,
                onBackClick = onBackClick,
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .background(white)
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 36.dp)
        ) {
            UserTopInfoItem(
                profileImageUrl = profile?.profileImage ?: "",
                name = profile?.name ?: "",
                studentId = profile?.studentId ?: "",
                department = profile?.department ?: "",
                phone = profile?.phone ?: "",
                setProfileImage = setProfileImage,
            )
            HorizontalDividerItem(thickness = 12.dp, color = primary10)
            UserBottomInfoItem(
                daysOfUser = profile?.daysOfUse ?: emptyList(),
                userRole = profile?.userRole
            )
            HorizontalDividerItem(thickness = 12.dp, color = primary10)
            HistoryHeaderItem()
            items(groups) { group ->
                HistoryGroup(group = group)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() = MatePreview {
    ProfileLookUpScreen(
        profile = Profile(
            profileImage = "",
            name = "강금실",
            studentId = "123a123",
            department = "미학과",
            phone = "01012345678",
            daysOfUse = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
            userRole = MemberRole.DRIVER,
            recentTickets = listOf(
                Ticket(
                    id = 1L,
                    thumbnail = "",
                    startArea = StartArea.DEAGU,
                    dayStatus = DayStatus.AM,
                    startTime = System.currentTimeMillis(),
                    maximumNumber = 4,
                    currentNumber = 3,
                    status = TicketStatus.After,
                    costType = TicketType.Cost
                ),
                Ticket(
                    id = 2L,
                    thumbnail = "",
                    startArea = StartArea.DEAGU,
                    dayStatus = DayStatus.AM,
                    startTime = System.currentTimeMillis() - 10000,
                    maximumNumber = 4,
                    currentNumber = 3,
                    status = TicketStatus.After,
                    costType = TicketType.Free
                ),
                Ticket(
                    id = 3L,
                    thumbnail = "",
                    startArea = StartArea.DEAGU,
                    dayStatus = DayStatus.AM,
                    startTime = System.currentTimeMillis() - 20000,
                    maximumNumber = 4,
                    currentNumber = 3,
                    status = TicketStatus.After,
                    costType = TicketType.Free
                ),
                Ticket(
                    id = 4L,
                    thumbnail = "",
                    startArea = StartArea.DEAGU,
                    dayStatus = DayStatus.AM,
                    startTime = System.currentTimeMillis() - 11110000,
                    maximumNumber = 4,
                    currentNumber = 3,
                    status = TicketStatus.After,
                    costType = TicketType.Free
                ),
                Ticket(
                    id = 5L,
                    thumbnail = "",
                    startArea = StartArea.DEAGU,
                    dayStatus = DayStatus.AM,
                    startTime = System.currentTimeMillis() - 11120000,
                    maximumNumber = 4,
                    currentNumber = 3,
                    status = TicketStatus.After,
                    costType = TicketType.Cost
                ),
            )
        ),
        setProfileImage = {},
        onEditClick = {},
        onBackClick = {},
    )
}