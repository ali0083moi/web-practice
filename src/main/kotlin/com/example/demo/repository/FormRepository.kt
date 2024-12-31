package com.example.demo.repository

import com.example.demo.model.Form
import org.springframework.data.jpa.repository.JpaRepository

interface FormRepository : JpaRepository<Form, Long> {
    fun findAllByPublishedTrue(): List<Form>
}