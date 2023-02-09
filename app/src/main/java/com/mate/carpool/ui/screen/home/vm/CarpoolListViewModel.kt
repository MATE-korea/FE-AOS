package com.mate.carpool.ui.screen.home.vm

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.MemberRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.screen.home.item.CarpoolListUiState
import com.mate.carpool.ui.screen.home.item.TicketListState
import com.mate.carpool.ui.screen.home.item.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CarpoolListViewModel @Inject constructor(
    private val carpoolListRepository: CarpoolListRepository,
    private val memberRepository: MemberRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CarpoolListUiState.getInitValue())
    val uiState get() = _uiState.asStateFlow()

    init {
        getCarpoolList()
        getMemberModel()
    }

    fun onRefresh(event: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(refreshState = true)
            }

            getMemberModel()
            getCarpoolList()
            delay(1000)

            _uiState.update { state ->
                state.copy(refreshState = false)
            }

            emitEvent(event)
        }
    }

    private fun getCarpoolList() = carpoolListRepository.getTicketList().onEach {
            when (it.isNotEmpty()) {
                true -> {
                    _uiState.update { state ->
                        state.copy(ticketList = it.map { ticketModel ->
                            ticketModel.asTicketListState()
                        })
                    }
                }
                false -> {
                    //TODO 데이터없을때 처리 ( 화면상에 메세지 표시하는등 )
                }
            }
        }.catch { e ->
            when (e) {
                is HttpException -> {
                    //TODO 결과값 불러올수 없을때 처리
                }
                else -> {
                    //TODO 네트워크 예외 이외의 다른 예외처리
                }
            }
        }.launchIn(viewModelScope)


    private fun getMemberModel() = memberRepository.getMyProfileNew().onEach { userModel ->
            _uiState.update { state ->
                state.copy(userState = userModel.asUserStateItem())
            }
        }.catch { e ->
            when (e) {
                is HttpException -> {
                    //TODO 결과값 불러올수 없을때 처리
                }
                else -> {
                    //TODO 네트워크 예외 이외의 다른 예외처리
                }
            }
        }.launchIn(viewModelScope)

}