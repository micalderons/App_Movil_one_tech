package com.example.one_teach.viewmodel
import androidx.lifecycle.ViewModel
import com.example.one_teach.model.ProductoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductoViewModel: ViewModel() {
    private val _ui = MutableStateFlow(ProductoUiState())
    val ui = _ui.asStateFlow()

    fun onNombre(s: String) = _ui.update { it.copy(nombre = s) }
    fun onPrecio(s: String) = _ui.update { it.copy(precio = s) }
    fun onDescripcion(s: String) = _ui.update { it.copy(descripcion = s) }
    fun onStock(s: String) = _ui.update { it.copy(stock = s) }
    fun onAcepta(b: Boolean) = _ui.update { it.copy(acepta = b) }

    fun valiar(): Boolean{
        val errs = mutableMapOf<String, String>()
        if (ui.value.nombre.isBlank()) errs["nombre"] = "Campo requerido"
        val precio = ui.value.precio.toDoubleOrNull()
        if (precio == null || precio <= 0) errs["precio"] = "Precio invalido"
        if (ui.value.descripcion.length <10) errs["descripcion"] = "Descripcion invalida"
        val stock = ui.value.stock.toIntOrNull()
        if (stock ==null || stock < 0) errs["stock"] = "Stock invalido"
        if (!ui.value.acepta) errs["acepta"] = "Debe aceptar los terminos"

        _ui.value = ui.value.copy(errores = errs)
        return errs.isEmpty()
    }
}

private inline fun <T> MutableStateFlow<T>.update(transform: (T) -> T) {
    value=transform(value)
}