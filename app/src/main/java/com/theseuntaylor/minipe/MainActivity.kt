package com.theseuntaylor.minipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.theseuntaylor.minipe.core.theme.MinipeTheme
import com.theseuntaylor.minipe.lib_login.ui.LoginScreen
import com.theseuntaylor.minipe.lib_taps.ui.TapsScreen
import com.theseuntaylor.minipe.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinipeTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold { padding ->
                        Box(
                            modifier = Modifier.padding(padding)
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = Screens.Login.route,
                                modifier = Modifier
                            ) {
                                composable(route = Screens.Login.route) {
                                    LoginScreen(
                                        navigateToTaps = {
                                            navController.navigate("taps")
                                        },
                                    )
                                }
                                composable(route = Screens.Taps.route) {
                                    TapsScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}