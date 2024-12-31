package com.example.demo.dto

data class FormDto(
    val id: Long? = null,
    val name: String,
    val published: Boolean = false,
    val fields: List<FieldDto> = emptyList(),
    val submitMethod: String,
    val submitEndpoint: String
)