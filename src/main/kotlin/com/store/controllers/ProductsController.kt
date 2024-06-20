package com.store.controllers

import com.store.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/products")
class ProductsController(
    private val productService: ProductService
) {
    @PostMapping
    fun addNewProduct(
        @RequestBody product: Map<String, Any>
    ): ResponseEntity<out Map<String, Any>> {
               return ResponseEntity(
                productService.addProduct(product),
                HttpStatus.CREATED
            )
    }

    @GetMapping
    fun getProductsBy(
        @RequestParam("type") type: String?
    ): ResponseEntity<out Any> {

        return ResponseEntity(
            productService.getProductsBy(type),
            HttpStatus.OK
        )
    }
}