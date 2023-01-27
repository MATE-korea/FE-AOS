package com.mate.carpool.ui.screen.home.vm

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.MemberRepository
import com.mate.carpool.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarpoolListViewModel @Inject constructor(
    private val carpoolListRepository: CarpoolListRepository,
    private val memberRepository: MemberRepository
    ) : BaseViewModel()
{
    private val mutableCarpoolListState = MutableStateFlow<List<TicketListModel>>(emptyList())
    val carpoolListState get() = mutableCarpoolListState.asStateFlow()

    private val mutableCarpoolExistState = MutableStateFlow(false)
    val carpoolExistState get() = mutableCarpoolExistState.asStateFlow()

    private val mutableMemberModelState = MutableStateFlow(MemberModel())
    val memberModelState get() = mutableMemberModelState.asStateFlow()

    fun getCarpoolList(){
        viewModelScope.launch {
            carpoolListRepository.getTicketList().collectLatest {
                when(it){
                    is ApiResponse.Loading -> {
                    }
                    is ApiResponse.SuccessResponse ->{
                        mutableCarpoolListState.emit(it.responseMessage)
                    }
                    is ApiResponse.FailResponse ->{
                    }
                    is ApiResponse.ExceptionResponse ->{
                    }
                }
            }
        }
    }

    fun getMemberModel(){
        viewModelScope.launch {
            memberRepository.getMemberInfo().collectLatest {
                when(it){
                    is ApiResponse.Loading -> {
                    }
                    is ApiResponse.SuccessResponse ->{
                        mutableMemberModelState.emit(it.responseMessage)
                        if(it.responseMessage.ticketList?.size!=0)
                            mutableCarpoolExistState.emit(true)
                        else
                            mutableCarpoolExistState.emit(false)
                    }
                    is ApiResponse.FailResponse ->{
                    }
                    is ApiResponse.ExceptionResponse ->{
                    }
                }
            }
        }
    }

    fun isTicketIsMineOrNot(id:Long) : Boolean{
        if(memberModelState.value.ticketList!=null){
            for(item in memberModelState.value.ticketList!!){
                if(item.id==id){
                    return true
                }
            }
        }
        return false
    }
}