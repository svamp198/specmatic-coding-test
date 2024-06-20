package com.store.service

import com.store.exception.ValidationException
import com.store.helpers.ProductHelper
import com.store.models.ProductDetails
import org.springframework.stereotype.Component

@Component
class ProductService(private var productList : HashMap<Int,ProductDetails> = HashMap(), private val productHelper: ProductHelper,) {

    internal fun addProduct(productRequest: Map<String, Any>): Map<String, Int> {
        val id = productList.size + 1
        val productWithId : MutableMap<String, Any> = productRequest as MutableMap
        productWithId["id"] = id
        val validProduct = productHelper.mapToProductDetails(productWithId)
        this.productList[id] = validProduct
        println("Successfully added product `${productList[id].toString()}`")
        return mapOf("id" to id)
    }

    fun getProductsBy(type: String?): List<ProductDetails> {
        println("Finding product with type `$type`")
        return when {
            type == null -> getProducts()
            productHelper.isValidType(type) -> productList.values.filter{product -> product.type == type }

            else -> throw ValidationException("Invalid product type `$type`", "/products?type=$type")
        }
    }

    private fun getProducts(): List<ProductDetails> {
        println("Finding all products ")
        return productList.values.map{it}
    }

}