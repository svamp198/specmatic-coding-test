package com.store.exception

class ValidationException(message: String, val path: String = "/products") : RuntimeException(message)
