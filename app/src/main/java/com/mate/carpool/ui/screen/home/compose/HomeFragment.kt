package com.mate.carpool.ui.screen.home.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.ui.composable.rememberLambda
import com.mate.carpool.ui.navigation.NavigationGraph
import com.mate.carpool.ui.theme.MateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MateTheme() {
                    this@HomeFragment.Content()
                }
            }
        }
    }

    @Composable
    private fun Content() {
        NavigationGraph(
            navController = rememberNavController(),
            onNavigateToCreateCarpool = rememberLambda {
                findNavController().navigate(R.id.action_homeFragment_to_createTicketBoardingAreaFragment)
            },
            onNavigateToProfileView = rememberLambda {
                findNavController().navigate(R.id.action_homeFragment_to_profileLookUpFragment)
            }
        )
    }
}
