package com.mate.carpool.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnNext = view.findViewById<Button>(R.id.btn_confirm)
        btnNext.setOnClickListener {
            findNavController().navigate(RegisterProfileFragmentDirections.actionRegisterProfileFragmentToRegisterSelectDayFragment())
        }
    }
}