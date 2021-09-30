package com.adammcneilly.toa.login.domain.model

@Suppress("UnusedPrivateMember")
@JvmInline
value class Email(private val email: String)

@Suppress("UnusedPrivateMember")
@JvmInline
value class Password(private val password: String)

data class Credentials(
    val email: Email,
    val password: Password,
)
