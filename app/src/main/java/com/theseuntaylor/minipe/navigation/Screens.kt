package com.theseuntaylor.minipe.navigation

sealed class Screens(
    val route: String,
) {
    data object Login : Screens(route = "login")
    data object Taps : Screens(route = "taps")
}