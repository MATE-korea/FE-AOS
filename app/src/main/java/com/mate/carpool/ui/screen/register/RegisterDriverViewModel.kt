package com.mate.carpool.ui.screen.register

import android.net.Uri
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.util.substring
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class RegisterDriverViewModel @Inject constructor(): BaseViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState.getInitValue())
    val uiState get() = _uiState

    fun setCarImage(image: Uri?) =
        _uiState.update { state ->
            state.copy(carImage = image, invalidCarImage = true)
        }

    fun setCarNumber(carNum: String) =
        _uiState.update { state ->
            val pattern = Regex("[ㄱ-ㅎ가-힣0-9]")
            val result = carNum
                .filter { pattern.matches(it.toString()) }
                .substring(7)
            if(result.length == 7)
                state.copy(carNumber = result, invalidCarNumber = true)
            else
                state.copy(carNumber = result, invalidCarNumber = false)
        }


    fun setPhoneNumber(phoneNum: String) =
        _uiState.update { state ->
            val pattern = Regex("[0-9]")
            val result = phoneNum
                .filter { pattern.matches(it.toString()) }
                .substring(11)
            if(result.length == 11)
                state.copy(phoneNumber = result, invalidPhoneNumber = true)
            else
                state.copy(phoneNumber = result, invalidPhoneNumber = false)
        }

    fun fetch(image: MultipartBody.Part) {
        // TODO fetch

        emitEvent(EVENT_REGISTERED_DRIVER_SUCCEED)
    }

    companion object {
        const val EVENT_REGISTERED_DRIVER_SUCCEED = "EVENT_REGISTERED_DRIVER_SUCCEED"
    }
}