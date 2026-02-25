package com.example.inventorymanagement.uii.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanagement.data.local.entity.Product
import com.example.inventorymanagement.data.repository.CategoryRepository
import com.example.inventorymanagement.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) :
    ViewModel() {

    private var _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private var _categoryName = MutableStateFlow<String?>(null)
    val categoryName: StateFlow<String?> = _categoryName.asStateFlow()

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(productId)

            _product.value?.let { product ->
                val category = categoryRepository.getCategoryById(product.categoryId)
                _categoryName.value = category?.name
            }

        }
    }

    fun updateStock(newStock: Int) {
        viewModelScope.launch {
            _product.value?.let { currentProduct ->
                val updatedProduct = currentProduct.copy(stock = newStock)
                productRepository.updateProduct(updatedProduct)
                _product.value = updatedProduct
            }
        }
    }

    fun deleteProduct(onDeleted: () -> Unit) {
        viewModelScope.launch {
            _product.value?.let { product ->
                productRepository.deleteProduct(product)
                onDeleted()
            }
        }
    }
}