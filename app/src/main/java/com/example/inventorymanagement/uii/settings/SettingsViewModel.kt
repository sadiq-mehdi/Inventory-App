package com.example.inventorymanagement.uii.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanagement.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    fun clearAllProducts(){
        viewModelScope.launch {
            productRepository.deleteAllProducts()
        }
    }

}