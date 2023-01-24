package com.mate.carpool.ui.screen.home.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object PreviewCarpoolListViewModel: ViewModel(),CarpoolListViewModelInterface {

    override val carpoolListState: StateFlow<List<TicketListModel>>
        get() = MutableStateFlow<List<TicketListModel>>(
            emptyList()
        ).asStateFlow()

    override val carpoolExistState: StateFlow<Boolean>
        get() = MutableStateFlow(false).asStateFlow()

    override val memberModelState: StateFlow<MemberModel>
        get() = MutableStateFlow(
            MemberModel()
        ).asStateFlow()

    override val isRefreshState: MutableState<Boolean> = mutableStateOf(false)

    override fun getCarpoolList() {
    }

    override fun getMemberModel() {
    }

    override fun isTicketIsMineOrNot(id: Long): Boolean {
        return false
    }
}