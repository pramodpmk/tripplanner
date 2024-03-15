package com.example.composeapp.ui.navigation

const val HOME_ROUTE = "home_page"
const val SEARCH_ROUTE = "search_page"
const val DETAIL_ROUTE = "detail_page/{searchKey}/{searchType}/{tripId}/{sourcePage}"
const val LOGIN_ROUTE = "login_page"
const val REGISTER_ROUTE = "register_page"
const val WELCOME_ROUTE = "welcome_page"
const val PROFILE_ROUTE = "profile_page"

const val PARAM_SEARCH = "searchKey"
const val SEARCH_TYPE = "searchType"
const val TRIP_ID = "tripId"

sealed class Screen(val route: String) {
    object Home : Screen(route = HOME_ROUTE)
    object Search : Screen(route = SEARCH_ROUTE)
    object Detail : Screen(route = DETAIL_ROUTE)
    object Login : Screen(route = LOGIN_ROUTE)
    object Register : Screen(route = REGISTER_ROUTE)
    object Welcome : Screen(route = WELCOME_ROUTE)
    object Profile : Screen(route = PROFILE_ROUTE)
}
