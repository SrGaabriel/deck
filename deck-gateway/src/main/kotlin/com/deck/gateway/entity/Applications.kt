package com.deck.gateway.entity

import com.deck.common.util.IntIdEnumSerializer
import com.deck.common.util.IntSerializationStrategy
import kotlinx.serialization.Serializable

// Received when application status is updated
@Serializable
public data class RawPartialApplication(val status: RawApplicationStatus)

@Serializable(RawApplicationStatus.Serializer::class)
public enum class RawApplicationStatus(public val id: Int) {
    Approved(1),
    Declined(2);

    public companion object Serializer :
        IntIdEnumSerializer<RawApplicationStatus>(IntSerializationStrategy(values().associateBy { it.id }))
}