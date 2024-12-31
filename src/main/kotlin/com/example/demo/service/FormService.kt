package com.example.demo.service

import com.example.demo.dto.FieldDto
import com.example.demo.dto.FormDto
import com.example.demo.model.Field
import com.example.demo.model.Form
import com.example.demo.repository.FormRepository
import com.example.demo.exception.FormNotFoundException
import com.example.demo.exception.FormValidationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FormService(
    private val formRepository: FormRepository
) {

    fun getAllForms(): List<FormDto> =
        formRepository.findAll().map { it.toDto() }

    fun getPublishedForms(): List<FormDto> =
        formRepository.findAllByPublishedTrue().map { it.toDto() }

    fun createForm(formDto: FormDto): FormDto {
        try {
            validateForm(formDto)
            val form = Form(
                name = formDto.name,
                published = formDto.published,
                submitMethod = formDto.submitMethod,
                submitEndpoint = formDto.submitEndpoint
            )
            formDto.fields.map { it.toEntity() }
                .forEach { form.addField(it) }
            return formRepository.save(form).toDto()
        } catch (e: Exception) {
            throw FormValidationException("Failed to create form: ${e.message}")
        }
    }

    private fun validateForm(formDto: FormDto) {
        if (formDto.name.isBlank()) {
            throw FormValidationException("Form name cannot be empty")
        }
        if (formDto.submitEndpoint.isBlank()) {
            throw FormValidationException("Submit endpoint cannot be empty")
        }
        // Add more validation as needed
    }

    @Transactional
    fun updateForm(id: Long, formDto: FormDto): FormDto {
        val existingForm = findFormById(id)
        existingForm.name = formDto.name
        existingForm.published = formDto.published
        existingForm.submitMethod = formDto.submitMethod
        existingForm.submitEndpoint = formDto.submitEndpoint
        return existingForm.toDto()
    }

    fun deleteForm(id: Long) {
        val form = findFormById(id)
        formRepository.delete(form)
    }

    fun getFormById(id: Long): FormDto =
        findFormById(id).toDto()

    fun getFieldsOfForm(id: Long): List<FieldDto> =
        findFormById(id).fields.map { it.toDto() }

    @Transactional
    fun updateFields(id: Long, fieldsDto: List<FieldDto>): List<FieldDto> {
        val form = findFormById(id)
        // Remove existing fields, then add the updated ones
        form.fields.clear()
        fieldsDto.map { it.toEntity() }
            .forEach { form.addField(it) }
        return form.fields.map { it.toDto() }
    }

    fun publishForm(id: Long) {
        val form = findFormById(id)
        form.published = true
        formRepository.save(form)
    }

    fun unpublishForm(id: Long) {
        val form = findFormById(id)
        form.published = false
        formRepository.save(form)
    }

    private fun findFormById(id: Long): Form =
        formRepository.findById(id)
            .orElseThrow { FormNotFoundException("Form with id=$id not found") }

    // Model -> DTO
    private fun Form.toDto(): FormDto =
        FormDto(
            id = this.id,
            name = this.name,
            published = this.published,
            fields = this.fields.map { it.toDto() },
            submitMethod = this.submitMethod,
            submitEndpoint = this.submitEndpoint
        )

    // Model -> DTO
    private fun Field.toDto(): FieldDto =
        FieldDto(
            id = this.id,
            name = this.name,
            label = this.label,
            type = this.type,
            defaultValue = this.defaultValue
        )

    // DTO -> Model
    private fun FieldDto.toEntity(): Field =
        Field(
            name = this.name,
            label = this.label,
            type = this.type,
            defaultValue = this.defaultValue
        )
}