package com.example.one_teach.ui.screens.registro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.one_teach.model.Usuario
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.viewmodel.RegistrationViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarDuration


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    vm: RegistrationViewModel = viewModel(),
    onRegisterSuccess: (Usuario) -> Unit = {}
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    var showPwd by remember { mutableStateOf(false) }
    var showConfirmPwd by remember { mutableStateOf(false) }
    val state = vm.ui
    val scroll = rememberScrollState()

    // Snackbar + scope para avisos globales
    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    AppScaffold(
        nav = navController,
        tittle = "Crear cuenta",
        bottomBar = { BottomBar(navController = navController, currentRoute = currentRoute) },
        snackbarHostState = snackbarHost
    ) { innerMod ->

        Column(
            modifier = innerMod
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Host del snackbar (puede estar en cualquier parte visible)
            SnackbarHost(hostState = snackbarHost)

            Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = state.fullName,
                onValueChange = vm::onFullNameChange,
                label = { Text("Nombre completo") },
                isError = state.fullNameError != null,
                supportingText = { state.fullNameError?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = state.rut,
                onValueChange = vm::onRutChange,
                label = { Text("RUT") },
                isError = state.rutError != null,
                supportingText = { state.rutError?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = vm::onEmailChange,
                label = { Text("Correo electrónico") },
                isError = state.emailError != null,
                supportingText = { state.emailError?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = state.phone,
                onValueChange = vm::onPhoneChange,
                label = { Text("Teléfono") },
                isError = state.phoneError != null,
                supportingText = { state.phoneError?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = state.direccion,
                onValueChange = vm::onAddressChange,
                label = { Text("Dirección") },
                isError = state.addressError != null,
                supportingText = { state.addressError?.let { Text(it) } },
                singleLine = false,
                minLines = 2,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = state.comuna,
                onValueChange = vm::onComunaChange,
                label = { Text("Comuna") },
                isError = state.comunaError != null,
                supportingText = { state.comunaError?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = state.region,
                onValueChange = vm::onRegionChange,
                label = { Text("Región") },
                isError = state.regionError != null,
                supportingText = { state.regionError?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = vm::onPasswordChange,
                label = { Text("Contraseña (mín. 6)") },
                isError = state.passwordError != null,
                supportingText = { state.passwordError?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (showPwd) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPwd = !showPwd }) {
                        Icon(
                            imageVector = if (showPwd) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showPwd) "Ocultar" else "Mostrar"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = state.confirmPassword,
                onValueChange = vm::onConfirmPasswordChange,
                label = { Text("Confirmar contraseña") },
                isError = state.confirmPasswordError != null,
                supportingText = { state.confirmPasswordError?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (showConfirmPwd) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showConfirmPwd = !showConfirmPwd }) {
                        Icon(
                            imageVector = if (showConfirmPwd) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showConfirmPwd) "Ocultar" else "Mostrar"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val user = vm.submitOrErrors()
                    if (user != null) {
                        scope.launch {

                            snackbarHost.showSnackbar(
                                message = "Cuenta creada con éxito",
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )

                            onRegisterSuccess(user)
                            navController.navigate(Route.Perfil.path) {
                                popUpTo(Route.Register.path) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    } else {
                        val msg = state.fullNameError
                            ?: state.rutError
                            ?: state.emailError
                            ?: state.phoneError
                            ?: state.addressError
                            ?: state.comunaError
                            ?: state.regionError
                            ?: state.passwordError
                            ?: state.confirmPasswordError
                            ?: "Por favor corrige los campos en rojo"

                        scope.launch {
                            snackbarHost.showSnackbar(
                                message = msg,
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarme")
            }


        }
    }
}
