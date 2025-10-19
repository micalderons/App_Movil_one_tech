package com.example.one_teach.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.one_teach.R

val Roboto = FontFamily(
    Font(R.font.robotoregular, FontWeight.Normal),
    Font(R.font.robotobold, FontWeight.Bold)
)

val Orbitron = FontFamily(
    Font(R.font.orbitronregular, FontWeight.Normal),
    Font(R.font.orbitronbold, FontWeight.Bold)
)

val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Orbitron,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
)