package com.example.one_teach.repository

import com.example.one_teach.R
import com.example.one_teach.model.ProductoUiState

class ProductRepository {

    private val products = listOf(
        ProductoUiState(
            "JM001", "Juegos de Mesa", "Catan", 29990,
            "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.",
            R.drawable.catan
        ),
        ProductoUiState(
            "JM002", "Juegos de Mesa", "Carcassonne", 24990,
            "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.",
            R.drawable.carcassonne
        ),
        ProductoUiState(
            "AC001", "Accesorios", "Controlador Inalámbrico Xbox Series X", 59990,
            "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.",
            R.drawable.xboxcontroller
        ),
        ProductoUiState(
            "AC002", "Accesorios", "Auriculares Gamer HyperX Cloud II", 79990,
            "Proporcionan un sonido envolvente de calidad con un micrófono desmontable y almohadillas de espuma viscoelástica para mayor comodidad durante largas sesiones de juego.",
            R.drawable.hyperx
        ),
        ProductoUiState(
            "CO001", "Consolas", "PlayStation 5", 549990,
            "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.",
            R.drawable.playstation5
        ),
        ProductoUiState(
            "CG001", "Computadores Gamers", "PC Gamer ASUS ROG Strix", 1299990,
            "Un potente equipo diseñado para los gamers más exigentes, equipado con los últimos componentes para ofrecer un rendimiento excepcional en cualquier juego.",
            R.drawable.pcasus
        ),
        ProductoUiState(
            "SG001", "Sillas Gamers", "Silla Gamer Secretlab Titan", 349990,
            "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable para sesiones de juego prolongadas.",
            R.drawable.sillagamer
        ),
        ProductoUiState(
            "MS001", "Mouse", "Mouse Gamer Logitech G502 HERO", 49990,
            "Con sensor de alta precisión y botones personalizables, este mouse es ideal para gamers que buscan un control preciso y personalización.",
            R.drawable.logitecmouse
        ),
        ProductoUiState(
            "MP001", "Mousepad", "Mousepad Razer Goliathus Extended Chroma", 29990,
            "Ofrece un área de juego amplia con iluminación RGB personalizable, asegurando una superficie suave y uniforme para el movimiento del mouse.",
            R.drawable.mousepad
        ),
        ProductoUiState(
            "PP001", "Poleras Personalizadas", "Polera Gamer Personalizada 'Level-Up'", 14990,
            "Una camiseta cómoda y estilizada, con la posibilidad de personalizarla con tu gamer tag o diseño favorito.",
            R.drawable.polera
        )
    )

    fun getProducts(): List<ProductoUiState> = products
}
