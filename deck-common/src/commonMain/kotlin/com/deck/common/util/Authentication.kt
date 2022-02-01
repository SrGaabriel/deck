package com.deck.common.util

import com.deck.common.entity.RawSelfUser
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic

@Serializable
public data class Authentication(
    val email: String,
    val password: String,
    val getMe: Boolean
) {
    public companion object {
        @JvmStatic
        public fun extractDataFromCookie(cookie: String, index: Int): String =
            cookie.split(" ")[index].split("=")[1].split(";")[0]
    }
}

@Serializable
public data class AuthenticationResult(
    val self: RawSelfUser,
    val token: String,
    val midSession: String
) {
    public companion object {
        @JvmStatic
        public fun fromCookie(self: RawSelfUser, cookie: String): AuthenticationResult = AuthenticationResult(
            self,
            Authentication.extractDataFromCookie(cookie, 0),
            Authentication.extractDataFromCookie(cookie, 1)
        )
    }
}

@DeckDSL
public class AuthenticationBuilder {
    public lateinit var email: String
    public lateinit var password: String
    public fun toSerializableAuthentication(): Authentication = Authentication(email, password, true)
}
