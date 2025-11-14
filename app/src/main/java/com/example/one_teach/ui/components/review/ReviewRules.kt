package com.example.one_teach.ui.components.review


fun isValidRating(rating: Int): Boolean =
    rating in 1..5


fun isValidComment(comment: String): Boolean =
    comment.trim().length >= 5


fun canSubmitReview(comment: String, rating: Int): Boolean =
    isValidRating(rating) && isValidComment(comment)


fun averageRating(ratings: List<Int>): Double {
    if (ratings.isEmpty()) return 0.0

    val valid = ratings.filter { isValidRating(it) }
    if (valid.isEmpty()) return 0.0
    return valid.sum().toDouble() / valid.size
}
