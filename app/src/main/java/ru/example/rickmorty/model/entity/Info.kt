package ru.example.rickmorty.model.entity

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
