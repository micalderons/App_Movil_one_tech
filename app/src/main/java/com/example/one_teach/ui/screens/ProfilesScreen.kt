@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.one_teach.ui.screens

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import java.io.File
import androidx.activity.result.PickVisualMediaRequest
import com.example.one_teach.viewmodel.ProfileUi






@Composable
fun ProfileScreen(
    nav: NavController,
    vm: ProfileViewModel = viewModel()
) {
    val ui = vm.ui
    val ctx = LocalContext.current


    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { vm.updatePhoto(it.toString()) }
    }


    var cameraUri by remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { ok ->
        if (ok) cameraUri?.toString()?.let { vm.updatePhoto(it) }
    }


    val requestCameraPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {

            cameraUri = createImageUri(ctx)
            cameraUri?.let { cameraLauncher.launch(it) }
        } else {
            scope.launch { snackbarHost.showSnackbar("Permiso de cámara denegado") }
        }
    }
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            galleryLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            scope.launch { snackbarHost.showSnackbar("Permiso de galería denegado") }
        }
    }



    AppScaffold(
        nav = nav,
        tittle = "Perfil",
        snackbarHostState = snackbarHost,
        bottomBar = { BottomBar(navController = nav, currentRoute = Route.Perfil.path) }
    ) { inner ->
        Column(
            modifier = inner
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Foto de perfil
            Image(
                painter = rememberAsyncImagePainter(ui.photoUri),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = {

                    galleryLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) {
                    Text("Elegir de galería")
                }


                OutlinedButton(onClick = {
                    requestCameraPermission.launch(Manifest.permission.CAMERA)
                }) { Text("Tomar foto") }
            }


            if (!ui.editing) {
                Button(onClick = { vm.setEditing(true) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Editar perfil")
                }
            } else {
                Button(onClick = {
                    vm.saveChanges()
                    scope.launch { snackbarHost.showSnackbar("Cambios guardados") }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Guardar cambios")
                }
            }


            OutlinedTextField(
                value = ui.fullname,
                onValueChange = vm::updateFullname,
                label = { Text("Nombre completo") },
                enabled = ui.editing,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ui.email,
                onValueChange = { /* email no editable por defecto */ },
                label = { Text("Correo") },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ui.rut,
                onValueChange = { /* rut no editable por defecto */ },
                label = { Text("RUT") },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ui.phone,
                onValueChange = vm::updatePhone,
                label = { Text("Teléfono") },
                enabled = ui.editing,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ui.direccion,
                onValueChange = vm::updateDireccion,
                label = { Text("Dirección") },
                enabled = ui.editing,
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = ui.region,
                    onValueChange = vm::updateRegion,
                    label = { Text("Región") },
                    enabled = ui.editing,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = ui.comuna,
                    onValueChange = vm::updateComuna,
                    label = { Text("Comuna") },
                    enabled = ui.editing,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


private fun createImageUri(ctx: Context): Uri? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "profile_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        ctx.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    } else {
        val imagesDir = File(ctx.cacheDir, "images").apply { mkdirs() }
        val imageFile = File(imagesDir, "profile_${System.currentTimeMillis()}.jpg")
        FileProvider.getUriForFile(ctx, "${ctx.packageName}.fileprovider", imageFile)
    }
}
@Composable
private fun ProfileForm(
    ui: ProfileUi,
    enabled: Boolean,
    onFullname: (String) -> Unit,
    onPhone: (String) -> Unit,
    onDireccion: (String) -> Unit,
    onRegion: (String) -> Unit,
    onComuna: (String) -> Unit
) {
    OutlinedTextField(
        value = ui.fullname,
        onValueChange = onFullname,
        label = { Text("Nombre completo") },
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = ui.email,
        onValueChange = {}, // email no editable
        label = { Text("Correo") },
        enabled = false,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = ui.rut,
        onValueChange = {}, // rut no editable
        label = { Text("RUT") },
        enabled = false,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = ui.phone,
        onValueChange = onPhone,
        label = { Text("Teléfono") },
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = ui.direccion,
        onValueChange = onDireccion,
        label = { Text("Dirección") },
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        minLines = 2
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = ui.region,
            onValueChange = onRegion,
            label = { Text("Región") },
            enabled = enabled,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = ui.comuna,
            onValueChange = onComuna,
            label = { Text("Comuna") },
            enabled = enabled,
            modifier = Modifier.weight(1f)
        )
    }
}
