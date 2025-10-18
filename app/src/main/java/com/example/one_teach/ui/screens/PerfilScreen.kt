package com.example.one_teach.ui.screens

import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.one_teach.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(nav: NavController, vm: PerfilViewModel){
    val foto by vm.foto.collectAsState()
    var cameraUri by remember { mutableStateOf<Uri?>(null) }

    val pick = rememberLauncherForActivityResult(GetContent()) {uri ->
        uri?.let { vm.setFromGallery(it) }
    }
}