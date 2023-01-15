package com.mate.carpool.ui.screen.reservePassenger

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mate.carpool.R
import com.mate.carpool.databinding.ItemviewPassengerInfoPopupBinding
import com.mate.carpool.ui.base.BindPopUpDialogFragment

class TicketPassengerPopUp(location:IntArray)
    : BindPopUpDialogFragment<ItemviewPassengerInfoPopupBinding>(location,R.layout.itemview_passenger_info_popup) {

    override fun bindFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemviewPassengerInfoPopupBinding = ItemviewPassengerInfoPopupBinding.inflate(inflater,container,false)

    override fun initView() {
        binding.tvReport.setOnClickListener {
            dismiss()
        }
    }

    override fun subScribeUi() {
    }
}