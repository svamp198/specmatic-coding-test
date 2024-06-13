package com.store.service

import com.store.controllers.Products
import com.store.models.Product
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap

@Component
class ProductService(private var productList : HashMap<Int,Product> = HashMap()) {

    internal fun addProduct(product: MutableMap<String, Any>): Map<String, Int> {
        val id = if(productList.isEmpty()) 1 else productList.size
        product["id"] = id
        val validProduct = Product.from(product)
        this.productList[id] = validProduct
        return mapOf("id" to productList.size-1)
    }

    fun getProductsBy(type: String): List<Product> {
        return productList.values.filter{product -> product.type == type }
    }

    fun getProducts(): List<Product> {
        return productList.values.map{it}
    }

}