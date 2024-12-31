package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class Form(
    var name: String,
    var published: Boolean = false,
    var submitMethod: String,
    var submitEndpoint: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(
        mappedBy = "form",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var fields: MutableList<Field> = mutableListOf()

    // Add no-args constructor for JPA
    protected constructor() : this(
        name = "",
        published = false,
        submitMethod = "",
        submitEndpoint = ""
    )

    fun addField(field: Field) {
        fields.add(field)
        field.form = this
    }
}