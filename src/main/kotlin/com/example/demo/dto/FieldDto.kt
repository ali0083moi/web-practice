package com.example.demo.dto

import com.example.demo.model.FieldType

data class FieldDto(
    val id: Long? = null,
    val name: String,
    val label: String,
    val type: FieldType,
    val defaultValue: String? = null
)