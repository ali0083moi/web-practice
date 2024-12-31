package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Field(
    var name: String,
    var label: String,
    @Enumerated(EnumType.STRING)
    var type: FieldType,
    var defaultValue: String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    lateinit var form: Form

    // Add no-args constructor for JPA
    protected constructor() : this(
        name = "",
        label = "",
        type = FieldType.TEXT,
        defaultValue = null
    )
}