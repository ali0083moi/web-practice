package com.example.demo.repository

import com.example.demo.model.Field
import org.springframework.data.jpa.repository.JpaRepository

interface FieldRepository : JpaRepository<Field, Long>