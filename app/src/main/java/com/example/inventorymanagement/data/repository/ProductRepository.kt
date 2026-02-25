package com.example.inventorymanagement.data.repository

import com.example.inventorymanagement.data.local.dao.ProductDao
import com.example.inventorymanagement.data.local.entity.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao){

    suspend fun insertProduct(product: Product){
        productDao.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    suspend fun getProductById(id: Int?): Product? {
        return productDao.getProductById(id)
    }

    suspend fun getProductByBarcode(barcode: String): Product? {
        return productDao.getProductByBarcode(barcode)
    }

    suspend fun deleteAllProducts(){
        productDao.deleteAllProducts()
    }

}