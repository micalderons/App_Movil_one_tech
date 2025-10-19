package com.example.one_teach.ui.screens

import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.ui.components.ImagenPerfil
import com.example.one_teach.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(nav: NavController, vm: PerfilViewModel) {
    val foto by vm.foto.collectAsState()
    var cameraUri by remember { mutableStateOf<Uri?>(null) }

    val pick = rememberLauncherForActivityResult(GetContent()) { uri ->
        uri?.let { vm.setFromGallery(it) }
    }

    val takePicture = rememberLauncherForActivityResult(TakePicture()) { ok ->
        if (ok) cameraUri?.let { vm.setFromCamera(it) }
    }

    val ctx = LocalContext.current

    AppScaffold(
        nav = nav,
        tittle = "Perfil",
        bottomBar = {
            BottomBar(navController = nav, currentRoute = Route.Perfil.path)
        }
    ) { modifier ->
    Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImagenPerfil(uri = foto)

            Spacer(Modifier.height(16.dp))

            Button(onClick = { pick.launch("image/*") }) {
                Text("Elegir de galería")
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val uri = ctx.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        ContentValues().apply {
                            put(
                                MediaStore.Images.Media.DISPLAY_NAME,
                                "foto_${System.currentTimeMillis()}.jpg"
                            )
                            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        }
                    )
                    cameraUri = uri
                    if (uri != null) takePicture.launch(uri)
                }
            ) {
                Text("Tomar foto")
            }
        }
    }
}
