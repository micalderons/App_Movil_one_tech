package com.example.one_teach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_teach.model.ProductoUiState
import com.example.one_teach.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: ProductRepository = ProductRepository() // usa TU repo
) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductoUiState>>(emptyList())
    val products: StateFlow<List<ProductoUiState>> = _products.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    val selectedCategory = MutableStateFlow<String?>(null)

    init {
        viewModelScope.launch {
            val list = repo.getProducts()
            _products.value = list
            _categories.value = list.map { it.category }.distinct()
        }
    }
}
