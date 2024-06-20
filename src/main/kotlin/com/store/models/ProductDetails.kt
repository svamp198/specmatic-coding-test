package com.store.models

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ProductDetails(
    @field:NotNull(message = "Id must not be null")
    val id: Int,
    @field:NotNull(message = "Name must not be null")
    @field:Size(min = 1, message = "Name must not be empty")
    val name: String,
    @field:NotNull(message = "Type must not be null")
    val type: String,
    @field:NotNull(message = "Inventory is mandatory")
    val inventory: Int,
    @field:NotNull(message = "Cost is mandatory")
    val cost: Int
){

    override fun toString(): String {
        return "ProductDetails(id=$id, name='$name', type='$type', inventory=$inventory, cost=$cost)"
    }
}
