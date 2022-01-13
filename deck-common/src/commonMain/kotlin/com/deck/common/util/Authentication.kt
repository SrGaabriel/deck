package com.deck.common.util

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic

@Serializable
data class Authentication(
    val email: String,
    val password: String
) {
    companion object {
        @JvmStatic
        fun extractDataFromCookie(cookie: String, index: Int) =
            cookie.split(" ")[index].split("=")[1].split(";")[0]
    }
}

@Serializable
data class AuthenticationResult(
    val token: String,
    val midSession: String
) {
    companion object {
        @JvmStatic
        fun fromCookie(cookie: String) = AuthenticationResult(
            Authentication.extractDataFromCookie(cookie, 0),
            Authentication.extractDataFromCookie(cookie, 1)
        )
    }
}

@DeckDSL
class AuthenticationBuilder {
    lateinit var email: String
    lateinit var password: String
    fun toSerializableAuthentication() = Authentication(email, password)
}