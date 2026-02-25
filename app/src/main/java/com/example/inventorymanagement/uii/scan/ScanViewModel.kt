package com.example.inventorymanagement.uii.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanagement.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    fun searchProductByBarcode(
        barcode: String,
        onFound: (Int) -> Unit,
        onNotFound: (String) -> Unit
    ) {
        viewModelScope.launch {
            val product = productRepository.getProductByBarcode(barcode)
            if (product != null) {
                onFound(product.id)
            } else {
                onNotFound(barcode)
            }
        }
    }
}