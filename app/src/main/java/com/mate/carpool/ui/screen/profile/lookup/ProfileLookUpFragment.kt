package com.mate.carpool.ui.screen.profile.lookup

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mate.carpool.ui.base.BaseComposeFragment
import com.mate.carpool.ui.composable.rememberLambda
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileLookUpFragment : BaseComposeFragment<ProfileLookUpViewModel>() {

    private val args: ProfileLookUpFragmentArgs by navArgs()

    override val viewModel: ProfileLookUpViewModel by viewModels()
    override val useActionBar: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.needRefresh) {
            viewModel.fetch()
        }
    }

    @Composable
    override fun Content() {
        val profile by viewModel.profile.collectAsState()

        ProfileLookUpScreen(
            profile = profile,
            setProfileImage = viewModel::setProfileImage,
            onEditClick = rememberLambda {
                profile ?: kotlin.run {
                    showSnackbar("로딩중입니다. 잠시만 기다려주세요.")
                    return@rememberLambda
                }
                val action = ProfileLookUpFragmentDirections
                    .actionProfileLookUpFragmentToProfileModifyFragment(profile = profile!!)
                findNavController().navigate(action)
            },
            onBackClick = rememberLambda {
                val action =
                    ProfileLookUpFragmentDirections.actionProfileLookUpFragmentToHomeFragment(
                        ProfileLookUpViewModel.EVENT_FINISH
                    )
                findNavController().navigate(action)
            },
        )
    }
}