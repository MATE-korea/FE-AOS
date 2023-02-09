package com.mate.carpool.ui.screen.home.compose.component.bottomsheet

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.composable.DialogCustom
import com.mate.carpool.ui.composable.DialogState
import com.mate.carpool.ui.composable.HorizontalSpacer
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.LargeSecondaryButton
import com.mate.carpool.ui.screen.home.compose.component.ProfileImage
import com.mate.carpool.ui.screen.home.compose.component.dialog.PopupWindow
import com.mate.carpool.ui.screen.home.item.DriverState
import com.mate.carpool.ui.screen.home.item.PassengerState
import com.mate.carpool.ui.screen.home.item.TicketState
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.gray
import com.mate.carpool.ui.theme.neutral40
import com.mate.carpool.ui.theme.neutral50
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview
import com.mate.carpool.util.formatStartTimeAsDisplay
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun ReservationBottomSheetContent(
    ticketDetail: TicketState,
    userRole: MemberRole,
    userId: String,
    userPassengerId: String,
    selectedMemberPassengerId: String,
    setPassengerId: (String) -> Unit,
    setUserId: (String) -> Unit,
    onCloseBottomSheet: suspend () -> Unit,
    onBrowseOpenChatLink: () -> Unit,
    onNavigateToReportView: (String, String) -> Unit,
    onNavigateToTicketUpdate: () -> Unit,
    onRefresh: (String) -> Unit,
    deletePassengerFromTicket: (String, MemberRole) -> Unit,
    deleteMyTicket: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val popUpState = remember {
        mutableStateOf(false)
    }
    val popUpOffset = remember {
        mutableStateOf(Offset(0F, 0F))
    }

    val dialogUiState = remember {
        mutableStateOf(DialogState.getInitValue())
    }
    val showDialogState = remember {
        mutableStateOf(false)
    }
    val passengerId = remember {
        mutableStateOf("")
    }

    if (showDialogState.value)
        DialogCustom(
            dialogState = dialogUiState.value,
            onDismissRequest = { showDialogState.value = false }
        )

    Column(
        modifier = Modifier
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
            .graphicsLayer(alpha = 0.95f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_handle_bar),
                contentDescription = "HandleBar"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        onCloseBottomSheet()
                        popUpState.value = false
                    }
                },
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_x),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = gray
                )
            }
        }

        VerticalSpacer(height = 20.dp)

        TicketHeader(
            startArea = ticketDetail.startArea,
            endArea = ticketDetail.endArea
        )
        VerticalSpacer(height = 20.dp)
        TicketDetail(
            text1 = "출발 시간",
            text2 = "${ticketDetail.dayStatus.displayName} ${ticketDetail.startTime.formatStartTimeAsDisplay()}",
            text3 = "탑승 장소",
            text4 = ticketDetail.boardingPlace
        )
        VerticalSpacer(height = 20.dp)
        TicketDetail(
            text1 = "탑승 인원",
            text2 = ticketDetail.recruitPerson.toString() + "명",
            text3 = "비용",
            text4 = DecimalFormat("###,###").format(ticketDetail.ticketPrice)
        )
        VerticalSpacer(height = 20.dp)

        TicketOpenChat(
            onBrowseOpenChatLink = onBrowseOpenChatLink
        )

        VerticalSpacer(height = 20.dp)

        Row(modifier = Modifier.height(220.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "드라이버",
                    fontWeight = FontWeight.W700,
                    fontSize = 12.tu,
                    color = neutral50
                )
                VerticalSpacer(height = 4.dp)
                MemberInfo(
                    memberProfile = ticketDetail.driver.profileImage,
                    memberName = ticketDetail.driver.name,
                    memberId = ticketDetail.driver.id,
                    userId = userId,
                    iconDrawable = R.drawable.ic_hamburger_circle,
                    setUserId = setUserId,
                    setPassengerId = setPassengerId,
                    onChangePopUpState = { popUpState.value = !popUpState.value },
                    onSetPopUpOffset = { popUpOffset.value = it }
                )
            }

            HorizontalSpacer(width = 16.dp)

            Column(modifier = Modifier.weight(1f)) {
                TicketPassengerList(
                    userId = userId,
                    memberRecruitNumber = ticketDetail.recruitPerson,
                    memberGatherNumber = ticketDetail.passenger.size,
                    passengerList = ticketDetail.passenger,
                    modifier = Modifier.weight(1f),
                    setUserId = setUserId,
                    setPassengerId = setPassengerId,
                    onChangePopUpState = { popUpState.value = !popUpState.value },
                    onSetPopUpOffset = { popUpOffset.value = it },
                )
            }
        }

        VerticalSpacer(height = 20.dp)

        Text(
            text = "오후 12:00에 자동으로 카풀이 종료돼요.",
            fontSize = 12.tu,
            fontWeight = FontWeight.W400,
            color = neutral40,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "카풀에 문제(노쇼, 지각, 운전 미숙 등)가 있었다면 신고해주세요.",
            fontSize = 12.tu,
            fontWeight = FontWeight.W400,
            color = neutral40
        )

        Spacer(modifier = Modifier.weight(1f))

        TicketButton(
            ticketId = ticketDetail.id,
            userRole = userRole,
            userPassengerId = userPassengerId,
            deletePassengerFromTicket = deletePassengerFromTicket,
            deleteMyTicket = deleteMyTicket,
            onRefresh = onRefresh,
            onCloseBottomSheet = onCloseBottomSheet,
            onOpenDialog = { state ->
                dialogUiState.value = state.copy()
                showDialogState.value = true
            },
            onCloseDialog = { showDialogState.value = false },
            onNavigateToTicketUpdate = onNavigateToTicketUpdate
        )

        PopupWindow(
            dialogState = popUpState.value,
            popUpOffset = popUpOffset.value,
            userRole = userRole,
            selectedMemberPassengerId = selectedMemberPassengerId,
            deletePassengerFromTicket = deletePassengerFromTicket,
            onNavigateToReportView = { onNavigateToReportView(ticketDetail.id, userId) },
            onRefresh = onRefresh,
            onOpenDialog = { state ->
                dialogUiState.value = state.copy()
                showDialogState.value = true
            },
            onCloseDialog = { showDialogState.value = false }
        )
    }
}

/**
 * memberStudentId : 드라이버의 학번 혹은 패신저의 학번
 * userStudentId : 사용자의 학번
 * 이 두개가 일치하면? 햄버거 아이콘 없고, 일치하지 않으면? 햄버거 아이콘 생성
 */

@Composable
private fun MemberInfo(
    memberProfile: String,
    memberName: String,
    memberId: String,
    userId: String,
    passengerId: String = "",
    @DrawableRes iconDrawable: Int,
    setPassengerId: (String) -> Unit,
    setUserId: (String) -> Unit,
    onChangePopUpState: () -> Unit,
    onSetPopUpOffset: (Offset) -> Unit,
) {
    val clickState = remember {
        mutableStateOf(false)
    }

    Row(
        Modifier
            .height(44.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(
            memberProfile,
            Modifier
                .width(50.dp)
                .height(47.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape)
        )
        Spacer(modifier = Modifier.width(9.dp))
        Column(
            modifier = Modifier
                .height(34.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = memberName,
                fontSize = 14.tu,
                fontWeight = FontWeight.W400,
                modifier = Modifier
            )
        }
        if (userId != memberId)
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        onChangePopUpState()
                        setPassengerId(passengerId)
                        setUserId(userId)
                        clickState.value = true
                    },
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = iconDrawable),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .onGloballyPositioned {
                                if (clickState.value) {
                                    onSetPopUpOffset(
                                        Offset(
                                            x = it.positionInWindow().x,
                                            y = it.positionInWindow().y
                                        )
                                    )
                                    clickState.value = false
                                }
                            }
                    )
                }
            }
    }
}

@Composable
private fun TicketPassengerList(
    userId: String,
    passengerList: List<PassengerState>,
    memberRecruitNumber: Int,
    memberGatherNumber: Int,
    modifier: Modifier,
    setPassengerId: (String) -> Unit,
    setUserId: (String) -> Unit,
    onChangePopUpState: () -> Unit,
    onSetPopUpOffset: (Offset) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = "패신저 ($memberGatherNumber/$memberRecruitNumber)",
                fontWeight = FontWeight.W700,
                fontSize = 12.tu,
                color = neutral50
            )
            VerticalSpacer(height = 4.dp)
        }
        itemsIndexed(passengerList, key = { index, item -> item.passengerId }) { index, item ->
            MemberInfo(
                memberProfile = item.profileImage,
                memberName = item.name,
                memberId = item.id,
                userId = userId,
                setUserId = setUserId,
                passengerId = item.passengerId,
                iconDrawable = R.drawable.ic_hamburger_circle,
                onChangePopUpState = onChangePopUpState,
                onSetPopUpOffset = onSetPopUpOffset,
                setPassengerId = setPassengerId,
            )
            VerticalSpacer(height = 5.dp)
        }
    }
}

@Composable
private fun TicketOpenChat(
    onBrowseOpenChatLink: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_kakao),
            contentDescription = null,
            modifier = Modifier
                .width(38.dp)
                .height(36.dp)
                .padding(8.dp),
            tint = Color.Unspecified
        )
        Text(
            text = "오픈카톡 참여하기",
            fontWeight = FontWeight.W400,
            fontSize = 14.tu,
            color = black,
            modifier = Modifier.weight(1f)
        )
        androidx.compose.material3.IconButton(
            onClick = onBrowseOpenChatLink
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_navigate_next_small),
                contentDescription = null,
                tint = gray
            )
        }
    }
}

@Composable
private fun TicketButton(
    ticketId: String,
    userRole: MemberRole,
    userPassengerId: String,
    deleteMyTicket: (String) -> Unit,
    deletePassengerFromTicket: (String, MemberRole) -> Unit,
    onRefresh: (String) -> Unit,
    onCloseBottomSheet: suspend () -> Unit,
    onOpenDialog: (DialogState) -> Unit,
    onCloseDialog: () -> Unit,
    onNavigateToTicketUpdate: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Row() {
        when (userRole) {
            MemberRole.DRIVER -> {
                LargeSecondaryButton(
                    text = "수정하기",
                    onClick = { onNavigateToTicketUpdate() },
                    modifier = Modifier.weight(1f)
                )
                HorizontalSpacer(width = 8.dp)
                LargeSecondaryButton(
                    text = "티켓 삭제하기",
                    onClick = {
                        onOpenDialog(
                            DialogState(
                                header = "티켓을 삭제하시겠어요?",
                                content = "패신저에게 고지하셨나요? 이미 입금을 받으셨다면 환불처리를 진행해주세요.\n\n" +
                                        "패신저가 가 있는 상태에서 2회 이상 취소시,\n추후 서비스를 더 이상 이용하실 수 없습니다.\n\n" +
                                        "그래도 삭제하시겠습니까? ",
                                positiveMessage = "삭제",
                                negativeMessage = "취소",
                                onPositiveCallback = {
                                    coroutineScope.launch {
                                        deleteMyTicket(ticketId)
                                        onCloseDialog()
                                        onCloseBottomSheet()
                                        onRefresh(BaseViewModel.EVENT_READY)
                                    }
                                },
                                onNegativeCallback = onCloseDialog
                            )
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            MemberRole.PASSENGER -> {
                LargeSecondaryButton(
                    text = "예약 취소하기",
                    onClick = {
                        onOpenDialog(
                            DialogState(
                                header = "예약을 취소하시겠어요?",
                                content = "반복적이고 고의적인 카풀 예약 취소는\n추후 서비스 이용에 제한돼요.",
                                positiveMessage = "네, 취소할게요.",
                                negativeMessage = "아뇨, 유지할게요.",
                                onPositiveCallback = {
                                    coroutineScope.launch {
                                        deletePassengerFromTicket(
                                            userPassengerId,
                                            MemberRole.PASSENGER
                                        )
                                        onCloseBottomSheet()
                                        onRefresh(BaseViewModel.EVENT_READY)
                                    }
                                    onCloseDialog()
                                },
                                onNegativeCallback = onCloseDialog
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            MemberRole.ADMIN -> {}
        }

    }
}

/**
 * 사용자 = 패신저
 */

@Preview(showBackground = true)
@Composable
fun PreviewReservationBottomSheetContentPassenger() {
    MatePreview {
        ReservationBottomSheetContent(
            ticketDetail = TicketState(
                id = "1",
                startArea = "해운대",
                endArea = "동아대학교",
                boardingPlace = "해운대앞바다",
                dayStatus = DayStatus.AM,
                startTime = 25200L,
                openChatUrl = "",
                recruitPerson = 4,
                ticketPrice = 2000,
                driver = DriverState(
                    id = "207",
                    name = "홍길동",
                    profileImage = "",
                    email = "",
                    role = MemberRole.DRIVER,
                    phoneNumber = "",
                    carNumber = "",
                    carImage = ""
                ),
                passenger =
                listOf(
                    PassengerState(
                        id = "200",
                        name = "황진호",
                        profileImage = "",
                        email = "",
                        role = MemberRole.PASSENGER,
                        passengerId = "1"
                    ),
                    PassengerState(
                        id = "201",
                        name = "이수영",
                        profileImage = "",
                        email = "",
                        role = MemberRole.PASSENGER,
                        passengerId = "2"
                    ),
                    PassengerState(
                        id = "202",
                        name = "박신혜",
                        profileImage = "",
                        email = "",
                        role = MemberRole.PASSENGER,
                        passengerId = "3"
                    ),
                    PassengerState(
                        id = "203",
                        name = "박보영",
                        profileImage = "",
                        email = "",
                        role = MemberRole.PASSENGER,
                        passengerId = "4"
                    )
                )
            ),
            userRole = MemberRole.PASSENGER,
            userId = "200",
            userPassengerId = "",
            selectedMemberPassengerId = "",
            onBrowseOpenChatLink = {},
            onRefresh = {},
            deletePassengerFromTicket = { _, _ -> },
            setPassengerId = {},
            onNavigateToReportView = { _, _ -> },
            onCloseBottomSheet = {},
            onNavigateToTicketUpdate = {},
            deleteMyTicket = {},
            setUserId = {}
        )
    }
}

/**
 * 사용자 = 드라이버
 */

@Preview(showBackground = true)
@Composable
fun PreviewReservationBottomSheetContentDriver() {
    MatePreview {
        ReservationBottomSheetContent(
            ticketDetail = TicketState(
                id = "1",
                startArea = "해운대",
                endArea = "동아대학교",
                boardingPlace = "해운대앞바다",
                dayStatus = DayStatus.AM,
                startTime = 25200L,
                openChatUrl = "",
                recruitPerson = 3,
                ticketPrice = 2000,
                driver = DriverState(
                    id = "202",
                    name = "황진호",
                    profileImage = "",
                    email = "",
                    role = MemberRole.DRIVER,
                    phoneNumber = "",
                    carNumber = "",
                    carImage = ""
                ),
                passenger =
                listOf(
                    PassengerState(
                        id = "200",
                        name = "박보영",
                        profileImage = "",
                        email = "",
                        role = MemberRole.PASSENGER,
                        passengerId = "1"
                    ),
                    PassengerState(
                        id = "201",
                        name = "아이유",
                        profileImage = "",
                        email = "",
                        role = MemberRole.PASSENGER,
                        passengerId = "2"
                    ),
                )
            ),
            userRole = MemberRole.DRIVER,
            userId = "202",
            userPassengerId = "",
            selectedMemberPassengerId = "",
            onBrowseOpenChatLink = {},
            onRefresh = {},
            deletePassengerFromTicket = { _, _ -> },
            setPassengerId = {},
            onNavigateToReportView = { _, _ -> },
            onCloseBottomSheet = {},
            onNavigateToTicketUpdate = {},
            deleteMyTicket = {},
            setUserId = {}
        )
    }
}