package com.example.one_teach.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import com.example.one_teach.model.Review

class ReviewsSectionTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Composable
    private fun ThemedContent(content: @Composable () -> Unit) {
        MaterialTheme {
            content()
        }
    }

    @Test
    fun muestra_mensaje_cuando_no_hay_resenas() {
        composeRule.setContent {
            ThemedContent {
                ReviewsSection(reviews = emptyList())
            }
        }

        // Verifica que el mensaje vacío se muestra
        composeRule
            .onNodeWithTag("emptyReviewsMessage")
            .assertIsDisplayed()

        // Opcional: también podemos verificar el texto exacto
        composeRule
            .onNodeWithText(
                "Aún nadie ha realizado una reseña de este producto. ¡Puedes ser el primero!"
            )
            .assertIsDisplayed()
    }

    @Test
    fun no_muestra_mensaje_vacio_cuando_hay_resenas() {
        val reviews = listOf(
            Review(
                author = "Miguel",
                rating = 5,
                comment = "Muy buen producto"
            )
        )

        composeRule.setContent {
            ThemedContent {
                ReviewsSection(reviews = reviews)
            }
        }

        // El mensaje vacío NO debería existir
        composeRule
            .onNodeWithTag("emptyReviewsMessage")
            .assertDoesNotExist()

        // En cambio debería existir el texto de la reseña
        composeRule
            .onNodeWithText("Miguel (5★)")
            .assertIsDisplayed()
    }
}
