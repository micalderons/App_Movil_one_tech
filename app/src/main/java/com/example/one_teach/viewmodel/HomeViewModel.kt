package com.example.one_teach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_teach.model.ProductoUiState
import com.example.one_teach.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: ProductRepository = ProductRepository()
) : ViewModel() {


    private val _products = MutableStateFlow<List<ProductoUiState>>(emptyList())
    val products: StateFlow<List<ProductoUiState>> = _products.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()


    val categoriesForUi: StateFlow<List<String>> =
        products.combine(categories) { _, cats ->
            listOf("Todos") + cats
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf("Todos")
        )


    val filteredProducts: StateFlow<List<ProductoUiState>> =
        combine(products, searchQuery, selectedCategory) { list, query, cat ->
            val q = query.trim().lowercase()
            list.filter { p ->
                val byCat = (cat == "Todos") || (p.category == cat)
                val byName = q.isEmpty() || p.name.lowercase().contains(q)
                byCat && byName
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    init {
        viewModelScope.launch {
            val list = repo.getProducts()
            _products.value = list
            _categories.value = list.map { it.category }.distinct()
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onCategorySelected(cat: String) {
        _selectedCategory.value = cat
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }
}
