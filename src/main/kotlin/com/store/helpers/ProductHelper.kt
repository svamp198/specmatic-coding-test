package com.store.helpers

import com.store.models.ProductDetails
import com.store.models.ProductType
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties

@Component
class ProductHelper {

    internal fun isValidType(type : String) = ProductType.values().map { it.name }.contains(type)

    internal fun isValidRequest(product: MutableMap<String, Any>): Boolean {
        return if (checkForNonNullValues(product)) checkTypesOfEachValues(product) && isValidType(product["type"] as String)
        else false
    }

    private fun checkTypesOfEachValues(product: MutableMap<String, Any>): Boolean {
        product.forEach { entry ->
            println("type of `${entry.value}` is `${entry.value::class.simpleName}` should be `${ProductDetails::class.memberProperties.firstOrNull { it.name == entry.key }?.returnType?.simpleTypeName()}`")
            if (entry.value::class.simpleName != ProductDetails::class.memberProperties.firstOrNull { it.name == entry.key }?.returnType?.simpleTypeName())
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