package com.deck.gateway.com.deck.gateway.entity

import com.deck.common.util.IntIdEnumSerializer
import com.deck.common.util.IntSerializationStrategy
import kotlinx.serialization.Serializable

// Received when application status is updated
@Serializable
data class RawPartialApplication(val status: RawApplicationStatus)

@Serializable(RawApplicationStatus.Serializer::class)
enum class RawApplicationStatus(val id: Int) {
    Approved(1),
    Declined(2);

    companion object Serializer: IntIdEnumSerializer<RawApplicationStatus>(IntSerializationStrategy(values().associateBy { it.id }))
}