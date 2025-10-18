package com.example.one_teach.navigation

sealed class Route(val path: String){
    data object Root : Route ("root")
    data object Home : Route ("home")
    data object Perfil : Route ("perfil")
    data object Register : Route ("register")
    data object Resumen : Route ("resumen")

}