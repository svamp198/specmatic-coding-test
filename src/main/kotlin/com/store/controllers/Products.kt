package com.store.controllers

import com.store.helpers.ProductHelper
import com.store.models.ProductDetails
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
    private val productService: ProductService,
    private val productHelper: ProductHelper,

    ) {
    @PostMapping
    fun addNewProduct(
        @RequestBody product: MutableMap<String, Any>
    ): ResponseEntity<out Map<String, Any>> {

        return when {
            productHelper.isValidRequest(product) -> ResponseEntity<Map<String, Int>>(
                productService.addProduct(product),
                HttpStatus.CREATED
            )

            else -> ResponseEntity<Map<String, Any>>(
                hashMapOf(
                    "status" to 400,
                    "error" to "bad error",
                    "path" to "/products",
                    "timestamp" to LocalDateTime.now()
                ), HttpStatus.BAD_REQUEST
            )
        }
    }

    @GetMapping
    fun getProductsByType(
        @RequestParam("type") type: String?
    ): ResponseEntity<out Any> {

        return when {
            type == null -> ResponseEntity<List<ProductDetails>>(productService.getProducts(), HttpStatus.OK)
            productHelper.isValidType(type) -> ResponseEntity<List<ProductDetails>>(
                productService.getProductsBy(type),
                HttpStatus.OK
            )

            else -> ResponseEntity<Map<String, Any>>(
                hashMapOf(
                    "status" to 400,
                    "error" to "bad error",
                    "path" to "/products",
                    "timestamp" to LocalDateTime.now()
                ), HttpStatus.BAD_REQUEST
            )
        }
    }
}