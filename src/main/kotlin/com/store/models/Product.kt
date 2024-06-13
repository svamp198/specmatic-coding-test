package com.store.models

data class Product(
    val id: Int,
    val name: String,
    val type: String,
    val inventory: Int
){
    companion object{
        fun from(map: Map<String,Any>) = object {
            val id by map
            val name by map
            val type by map
            val inventory by map

            val product = Product(id as Int, name  as String, type as String, inventory  as Int)
        }.product
    }
}
