package com.example.one_teach.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.one_teach.R

@Composable
fun WelcomeScreen(
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    onGuest: () -> Unit
) {
    val cs = MaterialTheme.colorScheme
    val ty = MaterialTheme.typography
    val pill = RoundedCornerShape(28.dp)

    Surface(Modifier.fillMaxSize(), color = cs.background) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡Bienvenido a ONE-TECH!",
                style = ty.headlineLarge,              // Usa Orbitron Bold 32sp
                color = cs.onBackground
            )

            Spacer(Modifier.height(20.dp))

            Image(
                painter = painterResource(R.drawable.onetech),
                contentDescription = "Logo One-Tech",
                modifier = Modifier.size(160.dp)
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onRegister,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = pill,
                colors = ButtonDefaults.buttonColors(
                    containerColor = cs.primary,        // Azul marca
                    contentColor   = cs.onPrimary
                )
            ) { Text("Registrarte") }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onLogin,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = pill,
                colors = ButtonDefaults.buttonColors(
                    containerColor = cs.secondary,      // Cian secundario
                    contentColor   = cs.onSecondary
                )
            ) { Text("Iniciar sesión") }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = onGuest,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = pill,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = cs.onSurface
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp
                )
            ) { Text("Entrar como invitado") }
        }
    }
}

