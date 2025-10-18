package com.example.one_teach.viewmodel
import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PerfilViewModel : ViewModel(){
    private val _foto = MutableStateFlow<Uri?>(null)
    val foto = _foto.asStateFlow()

    fun setFromGallery(uri: Uri){
        _foto.value = uri
    }
    fun setFromCamera(uri: Uri){
        _foto.value = uri
    }
}
