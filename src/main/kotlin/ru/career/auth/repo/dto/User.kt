package ru.career.auth.repo.dto

import jakarta.persistence.*

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val firstName: String,
    val lastName: String,

    @Column(unique = true)
    val username: String,

    val password: String
)
