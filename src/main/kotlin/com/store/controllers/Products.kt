package com.store.controllers

import com.store.models.Product
import com.store.models.ProductType
import com.store.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.Properties
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties


@RestController
@RequestMapping("/products")
class Products(
    private val productService: ProductService
) {


    @PostMapping
    fun addNewProduct(
        @RequestBody product: MutableMap<String, Any>
    ): ResponseEntity<Map<String, Int>> {

        if (isValidRequest(product))
            return ResponseEntity<Map<String, Int>>(productService.addProduct(product), HttpStatus.CREATED)
        return ResponseEntity<Map<String, Int>>(mapOf("status" to 400), HttpStatus.BAD_REQUEST)
    }

    @GetMapping
    fun getProductsByType(
        @RequestParam("type") type: String?
    ): ResponseEntity<out Any> {

        if (type == null) return ResponseEntity<List<Product>>(productService.getProducts(), HttpStatus.OK)
        else if (isValidType(type))
                return ResponseEntity<List<Product>>(productService.getProductsBy(type), HttpStatus.OK)
        return ResponseEntity<Map<String, Any>>(
            hashMapOf(
                "status" to 400,
                "error" to "bad error",
                "path" to "/products",
                "timestamp" to LocalDateTime.now()
            ), HttpStatus.BAD_REQUEST
        )
    }

    private fun isValidType(type : String) = ProductType.values().map { it.name }.contains(type)

    private fun isValidRequest(product: MutableMap<String, Any>): Boolean {
        return if (checkForNonNullValues(product)) checkTypesOfEachValues(product) && isValidType(product["type"] as String)
        else false
    }

    private fun checkTypesOfEachValues(product: MutableMap<String, Any>): Boolean {
        product.forEach { entry ->
            println("type of `${entry.value}` is `${entry.value::class.simpleName}` should be `${Product::class.memberProperties.firstOrNull { it.name == entry.key }?.returnType?.simpleTypeName()}`")
            if (entry.value::class.simpleName != Product::class.memberProperties.firstOrNull { it.name == entry.key }?.returnType?.simpleTypeName())
                return false
        }
        return true
    }

    private fun KType.simpleTypeName(): String? {
        return (this.classifier as? KClass<*>)?.simpleName
    }

    private fun checkForNonNullValues(product: MutableMap<String, Any>): Boolean {
        product.forEach { entry ->
            println("entry key: `${entry.key}` and entry value: `${entry.value}`")
            if (entry.value == null) {
                return false
            }
        }
        return true
    }
}