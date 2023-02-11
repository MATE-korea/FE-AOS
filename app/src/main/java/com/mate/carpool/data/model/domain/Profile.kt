package com.mate.carpool.data.model.domain

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.mate.carpool.data.model.domain.Ticket
import com.mate.carpool.data.model.item.MemberRole
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek

@Stable
@Parcelize
data class Profile(
    val profileImage: String,
    val name: String,
    val studentId: String,
    val department: String,
    val phone: String,
    val daysOfUse: List<DayOfWeek>,
    val userRole: MemberRole,
    val recentTickets: List<Ticket>
) : Parcelable

