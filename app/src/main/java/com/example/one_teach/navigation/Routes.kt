package com.example.one_teach.navigation

sealed class Route(val path: String) {
    object Root     : Route("root")
    object Home     : Route("home")
    object Perfil   : Route("perfil")
    object Register : Route("register")
    object Resumen  : Route("resumen")
    object Buscar   : Route("buscar")
    object Mas      : Route("mas")
    object Perfiles : Route("perfiles")
    object Login : Route("login")


}

