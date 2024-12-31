package com.example.demo.exception

class FormNotFoundException(message: String) : RuntimeException(message)
class FormValidationException(message: String) : RuntimeException(message) 