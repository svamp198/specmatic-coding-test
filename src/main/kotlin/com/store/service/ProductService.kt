package com.store.service

import com.store.models.ProductDetails
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap

@Component
class ProductService(private var productList : HashMap<Int,ProductDetails> = HashMap()) {

    internal fun addProduct(product: MutableMap<String, Any>): Map<String, Int> {
        val id = productList.size + 1
        product["id"] = id
        val validProduct = ProductDetails.from(product)
        this.productList[id] = validProduct
        println("Successfully added product `${productList[id].toString()}`")
        return mapOf("id" to productList.size)
    }

    fun getProductsBy(type: String): List<ProductDetails> {
        println("Finding product with type `$type`")
        return productList.values.filter{product -> product.type == type }
    }

    fun getProducts(): List<ProductDetails> {
        println("Finding all product ")
        return productList.values.map{it}
    }

}