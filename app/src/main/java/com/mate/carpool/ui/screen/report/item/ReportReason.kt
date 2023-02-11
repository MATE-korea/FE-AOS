package com.mate.carpool.ui.screen.report.item

import androidx.compose.runtime.Stable

@Stable
enum class ReportReason(val description: String) {
    NON_PAYMENT("카풀 돈을 받지 못했어요."),
    SEXUAL_HARASSMENT("성희롱을 했어요."),
    ABUSE("욕설, 비속어를 사용했어요."),
    ETC("기타 (하단에 작성)")
}