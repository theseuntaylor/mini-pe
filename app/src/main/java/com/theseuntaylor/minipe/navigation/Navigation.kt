package com.theseuntaylor.minipe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateToTaps(navOptions: NavOptions? = null) {
    this.navigate(Screens.Taps.route, navOptions)
}