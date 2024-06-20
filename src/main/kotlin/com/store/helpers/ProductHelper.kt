package com.store.helpers

import com.store.exception.ValidationException
import com.store.models.ProductDetails
import com.store.models.AllowedProductTypes
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties

@Component
class ProductHelper {

    fun isValidType(type: String) = AllowedProductTypes.values().map { it.name }.contains(type.uppercase())

    fun mapToProductDetails(productRequestMap: Map<String, Any>): ProductDetails {
        val productDetailsClass = ProductDetails::class
        val productDetailsConstructor = productDetailsClass.constructors.first()
        val params = productDetailsConstructor.parameters.associateWith { property ->
            productRequestMap[property.name] ?: throw ValidationException("Missing value for ${property.name}")
        }

        validateTypeOfEachValueBeforeConversion(productRequestMap)

        return productDetailsConstructor.callBy(params)
    }

    private fun KType.simpleTypeName(): String? {
        return (this.classifier as? KClass<*>)?.simpleName
    }

    private fun validateTypeOfEachValueBeforeConversion(product: Map<String, Any>) {
        product.forEach { entry ->
            if (entry.value::class.simpleName != ProductDetails::class.memberProperties.firstOrNull { it.name == entry.key }?.returnType?.simpleTypeName())
                throw ValidationException("for key `${entry.key}`, type of `${entry.value}` is `${entry.value::class.simpleName}`, but should be `${ProductDetails::class.memberProperties.firstOrNull { it.name == entry.key }?.returnType?.simpleTypeName()}`")
        }
    }
}