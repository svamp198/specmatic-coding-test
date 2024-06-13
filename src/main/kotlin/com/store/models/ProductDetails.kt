package com.store.models

data class ProductDetails(
    val id: Int,
    val name: String,
    val type: String,
    val inventory: Int,
    val cost: Int
){
    companion object{
        fun from(map: Map<String,Any>) = object {
            val id by map
            val name by map
            val type by map
            val inventory by map
            val cost by map

            val product = ProductDetails(id as Int, name  as String, type as String, inventory  as Int, cost as Int)
        }.product
    }

    override fun toString(): String {
        return "ProductDetails(id=$id, name='$name', type='$type', inventory=$inventory, cost=$cost)"
    }
}
