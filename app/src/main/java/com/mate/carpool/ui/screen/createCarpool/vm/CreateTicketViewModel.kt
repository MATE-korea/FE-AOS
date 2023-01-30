package com.mate.carpool.ui.screen.createCarpool.vm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.model.dto.CreateCarpoolRequestDTO
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.service.APIService
import com.mate.carpool.util.formatStartDayMonthToDTO
import com.mate.carpool.util.formatStartTimeToDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CreateTicketViewModel @Inject constructor(@ApplicationContext private val context:Context, private val apiService: APIService):ViewModel() {

    val mutableTicketModel = MutableLiveData(TicketModel.getInitValue())
    val ticketModel : LiveData<TicketModel> get() = mutableTicketModel
    val boardingAreaButtonFlag:MutableLiveData<ArrayList<Boolean>> = MutableLiveData(arrayListOf(false,false))
    val boardingTimeButtonFlag:MutableLiveData<ArrayList<Boolean>> = MutableLiveData(arrayListOf(false,false,false))
    val openChatButtonFlag:MutableLiveData<ArrayList<Boolean>> = MutableLiveData(arrayListOf(false,false,false))

    val ticketStartDayMonth = MutableStateFlow("")
    val ticketStartTime = MutableStateFlow("")

    fun createCarpoolTicket(){
        viewModelScope.launch {
            ticketStartDayMonth.update { mutableTicketModel.value?.startTime?.formatStartDayMonthToDTO()?:"" }
            ticketStartTime.update { mutableTicketModel.value?.startTime?.formatStartTimeToDTO()?:"" }
            apiService.postTicketNew(CreateCarpoolRequestDTO(ticketModel.value!!)).enqueue(object : Callback<ResponseMessage>{
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>
                ) {
                    when(response.code()){
                        200->{
                            Toast.makeText(context, "카풀 생성 성공!" , Toast.LENGTH_SHORT).show()
                        }
                        403->{
                            Toast.makeText(context,"카풀 생성 실패 : 드라이버만 카풀을 생성할 수 있습니다.", Toast.LENGTH_SHORT).show()
                        }
                        409->{
                            Toast.makeText(context,"카풀 생성 실패 : 이미 카풀을 생성하셨습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    Log.d("test","실패: "+ t.message)
                }

            })
        }
    }
}