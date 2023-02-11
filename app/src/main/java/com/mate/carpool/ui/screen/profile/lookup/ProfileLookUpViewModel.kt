package com.mate.carpool.ui.screen.profile.lookup

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.Result
import com.mate.carpool.data.repository.MemberRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.data.model.domain.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileLookUpViewModel @Inject constructor(
    private val memberRepository: MemberRepository
) : BaseViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile = _profile.asStateFlow()

    init {
        fetch()
    }

    fun fetch() = memberRepository.getMyProfile().onEach { result ->

    }.launchIn(viewModelScope)

    // TODO 프로필 이미지 압축
    fun setProfileImage(part: MultipartBody.Part) {
        memberRepository.updateProfileImage(part).onEach { result ->
            when (result) {
                is Result.Loading -> {
                }

                is Result.Success -> {
                    emitSnackbar(SnackBarMessage(headerMessage = result.data.message))
                    fetch()
                }

                is Result.Error -> {
                    emitSnackbar(SnackBarMessage(headerMessage = result.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        const val EVENT_FINISH = "EVENT_FINISH"
    }

}