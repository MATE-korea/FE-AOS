package com.mate.carpool.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R

class RegisterDetailFragment : Fragment() {

    //todo_sypark 이부분 부터 작업 진행해주시면 됩니다.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnConfirm = view.findViewById<Button>(R.id.btn_confirm)
        btnConfirm.setOnClickListener {
            findNavController().navigate(RegisterDetailFragmentDirections.actionRegisterDetailFragmentToMainFragment())
        }

    }
}