package com.mate.carpool.data.model

import android.net.Uri
import androidx.databinding.ObservableField

/*
회원이름,학번,학과,전화번호,유형(드라이버,패신저),프로필사진,이동할요일
 */
data class UserModel(
    val name: String,
    var studentID:String,
    var studentDepartment:String,
    val studentPhone:ObservableField<String>,
    var studentType:String,
    var studentProfile:String?,
    var studentDayCodes: ArrayList<MemberTimetableRequestDTO>?
)