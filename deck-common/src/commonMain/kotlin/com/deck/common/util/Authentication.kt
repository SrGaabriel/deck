package com.deck.common.util

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic

@Serializable
public data class Authentication(
    val email: String,
    val password: String
) {
    public companion object {
        @JvmStatic
        public fun extractDataFromCookie(cookie: String, index: Int): String =
            cookie.split(" ")[index].split("=")[1].split(";")[0]
    }
}

@Serializable
public data class AuthenticationResult(
    val token: String,
    val midSession: String
) {
    public companion object {
        @JvmStatic
        public fun fromCookie(cookie: String): AuthenticationResult = AuthenticationResult(
            Authentication.extractDataFromCookie(cookie, 0),
            Authentication.extractDataFromCookie(cookie, 1)
        )
    }
}

@DeckDSL
public class AuthenticationBuilder {
    public lateinit var email: String
    public lateinit var password: String
    public fun toSerializableAuthentication(): Authentication = Authentication(email, password)
}