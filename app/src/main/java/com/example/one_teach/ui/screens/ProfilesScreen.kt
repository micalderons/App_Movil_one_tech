package com.example.one_teach.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.viewmodel.ProfilesViewModel
import com.example.one_teach.navigation.Route
import java.io.File

@Composable
fun ProfilesScreen(
    nav: NavController,
    vm: ProfilesViewModel = viewModel()
) {
    val users by vm.users.collectAsState()
    val ctx = LocalContext.current
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route


    // Si no hay usuario, ofrece ir a Registro (o redirige desde el NavHost, como ya hiciste)
    if (users.isEmpty()) {
        AppScaffold(
            nav = nav,
            tittle = "Perfil",
            bottomBar = { BottomBar(navController = nav, currentRoute = currentRoute) }
        ) { inner ->
            Column(
                modifier = inner.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Aún no tienes un perfil registrado.")
                Spacer(Modifier.height(12.dp))
                Button(onClick = { nav.navigate(Route.Register.path) }) {
                    Text("Registrarte / Iniciar sesión")
                }
            }
        }
        return
    }

    val user = users.first()
    var photoUri by remember { mutableStateOf(user.photoUri?.let(Uri::parse)) }


    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            photoUri = it
            vm.updatePhoto(user.email, it.toString())
        }
    }

    val galleryPermission =
        if (Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_IMAGES
        else Manifest.permission.READ_EXTERNAL_STORAGE

    val galleryPermLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            galleryLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    // --------------------- CÁMARA ---------------------
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { ok ->
        if (ok) {
            tempPhotoUri?.let { uri ->
                photoUri = uri
                vm.updatePhoto(user.email, uri.toString()) // guarda en DataStore
            }
        }
    }

    val cameraPermLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createTempImageUri(ctx)
            tempPhotoUri = uri
            cameraLauncher.launch(uri)
        }
    }

    // --------------------- UI ---------------------
    AppScaffold(
        nav = nav,
        tittle = "Perfil",
        bottomBar = { BottomBar(navController = nav, currentRoute = currentRoute) }
    ) { inner ->

        Column(
            modifier = inner
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Foto de perfil
            if (photoUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(ctx)
                        .data(photoUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.size(120.dp).clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp).clip(CircleShape),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Botón: Elegir de galería
            Button(
                onClick = { galleryPermLauncher.launch(galleryPermission) },
                shape = CircleShape
            ) {
                Text("Elegir de galería")
            }

            // Botón: Tomar foto
            Button(
                onClick = { cameraPermLauncher.launch(Manifest.permission.CAMERA) },
                shape = CircleShape
            ) {
                Text("Tomar foto")
            }

            Divider(Modifier.padding(top = 8.dp))

            // Datos del usuario
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("Nombre: ${user.fullname}")
                Text("RUT: ${user.rut}")
                Text("Correo: ${user.email}")
                Text("Teléfono: ${user.phone}")
                Text("Dirección: ${user.direccion}")
                Text("Región: ${user.region}")
                Text("Comuna: ${user.comuna}")
            }
        }
    }
}

// Crea un archivo temporal para la foto de la cámara y devuelve su URI
private fun createTempImageUri(ctx: Context): Uri {
    val image = File.createTempFile("profile_", ".jpg", ctx.cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    val authority = "${ctx.packageName}.fileprovider"
    return FileProvider.getUriForFile(ctx, authority, image)
}
