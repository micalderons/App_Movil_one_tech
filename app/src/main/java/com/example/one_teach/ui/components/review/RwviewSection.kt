package com.example.one_teach.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.one_teach.model.Review

/**
 * Sección de reseñas que:
 *  - Muestra un texto especial cuando la lista está vacía
 *  - Muestra las reseñas cuando existe al menos una
 */
@Composable
fun ReviewsSection(
    reviews: List<Review>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("reviewsSection")
    ) {
        Text(
            text = "Reseñas",
            style = MaterialTheme.typography.titleMedium
        )

        if (reviews.isEmpty()) {
            Text(
                text = "Aún nadie ha realizado una reseña de este producto. ¡Puedes ser el primero!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.testTag("emptyReviewsMessage")
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(reviews, key = { it.hashCode() }) { review ->
                    Text(
                        text = "${review.author} (${review.rating}★)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = review.comment,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
