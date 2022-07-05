package io.github.deck.common.entity

import io.github.deck.common.util.IntGenericId
import kotlinx.serialization.Serializable

@Serializable
public value class RawRoleId(public val id: IntGenericId)