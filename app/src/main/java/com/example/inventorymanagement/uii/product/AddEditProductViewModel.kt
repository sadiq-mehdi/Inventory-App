package com.example.inventorymanagement.uii.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanagement.data.local.entity.Product
import com.example.inventorymanagement.data.repository.CategoryRepository
import com.example.inventorymanagement.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val categories = categoryRepository.getAllCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Form fields
    private val _productName = MutableStateFlow("")
    val productName = _productName.asStateFlow()

    private val _barcode = MutableStateFlow("")
    val barcode = _barcode.asStateFlow()

    private val _stock = MutableStateFlow("")
    val stock = _stock.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<Int?>(null)
    val selectedCategoryId = _selectedCategoryId.asStateFlow()

    private val _minStock = MutableStateFlow("")
    val minStock = _minStock.asStateFlow()

    // Update functions
    fun updateProductName(name: String) {
        _productName.value = name
    }

    fun updateBarcode(code: String) {
        println("🔍 updateBarcode called with: $code")
        _barcode.value = code
    }

    fun updateStock(stockValue: String) {
        _stock.value = stockValue
    }

    fun updateSelectedCategory(categoryId: Int) {
        _selectedCategoryId.value = categoryId
    }

    fun updateMinStock(minStock: String) {
        _minStock.value = minStock
    }

    // Load existing product (for Edit mode)
    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            val product = productRepository.getProductById(productId)
            product?.let {
                _productName.value = it.name
                _barcode.value = it.barcode ?: ""
                _stock.value = it.stock.toString()
                _selectedCategoryId.value = it.categoryId
                _minStock.value = it.minStock.toString()
            }
        }
    }

    fun saveProduct(productId: Int?, onSaved: () -> Unit) {
        viewModelScope.launch {

            if (_productName.value.isBlank()) {
                return@launch
            }
            if (_selectedCategoryId.value == null) {
                return@launch
            }

            if (productId == null) {
                // Add Mode - Create new product
                val newProduct = Product(
                    id = 0,
                    name = _productName.value,
                    barcode = _barcode.value.ifBlank { null },
                    categoryId = _selectedCategoryId.value!!,
                    stock = _stock.value.toIntOrNull() ?: 0,
                    minStock = _minStock.value.toIntOrNull() ?: 5
                )
                productRepository.insertProduct(newProduct)
            } else {
                // Edit Mode - Update existing product
                val updatedProduct = Product(
                    id = productId,
                    name = _productName.value,
                    barcode = _barcode.value.ifBlank { null },
                    categoryId = _selectedCategoryId.value!!,
                    stock = _stock.value.toIntOrNull() ?: 0,
                    minStock = _minStock.value.toIntOrNull() ?: 5
                )
                productRepository.updateProduct(updatedProduct)
            }
            onSaved()
        }
    }
}