package com.example.money_machine.annotation

/**
 * This annotation is used to open Kotlin classes for mocking
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass

/**
 * Used on Kotlin classes that need to be open for mocking purposes
 */
@OpenClass
@Target(AnnotationTarget.CLASS)
annotation class TestOpen