package com.deck.core.util

public data class MemberPatch(
    val name: Difference<String>,
    val nickname: Difference<String>,
    val biography: Difference<String>,
    val tagline: Difference<String>,
    val avatar: Difference<String>,
    val banner: Difference<String>
)