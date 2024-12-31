package com.example.demo.controller

import com.example.demo.dto.FieldDto
import com.example.demo.dto.FormDto
import com.example.demo.service.FormService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import jakarta.validation.Valid

@RestController
@RequestMapping("/forms")
@Validated
class FormController(
    private val formService: FormService
) {

    @GetMapping
    fun getAllForms(): List<FormDto> =
        formService.getAllForms()

    @GetMapping("/published")
    fun getPublishedForms(): List<FormDto> =
        formService.getPublishedForms()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createForm(@Valid @RequestBody formDto: FormDto): FormDto =
        formService.createForm(formDto)

    @GetMapping("/{id}")
    fun getFormById(@PathVariable id: Long): FormDto =
        formService.getFormById(id)

    @PutMapping("/{id}")
    fun updateForm(
        @PathVariable id: Long,
        @RequestBody formDto: FormDto
    ): FormDto = formService.updateForm(id, formDto)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteForm(@PathVariable id: Long) {
        formService.deleteForm(id)
    }

    @GetMapping("/{id}/fields")
    fun getFieldsOfForm(@PathVariable id: Long): List<FieldDto> =
        formService.getFieldsOfForm(id)

    @PutMapping("/{id}/fields")
    fun updateFields(
        @PathVariable id: Long,
        @RequestBody fields: List<FieldDto>
    ): List<FieldDto> = formService.updateFields(id, fields)

    @PostMapping("/{id}/publish")
    fun publishForm(@PathVariable id: Long) {
        formService.publishForm(id)
    }

    @PostMapping("/{id}/unpublish")
    fun unpublishForm(@PathVariable id: Long) {
        formService.unpublishForm(id)
    }
}