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

@DeckDSL
class AuthenticationBuilder {
    lateinit var email: String
    lateinit var password: String
    fun toSerializableAuthentication() = Authentication(email, password)
}