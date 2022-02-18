package com.deck.rest.entity

import com.deck.common.entity.RawMessageContent
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class RawChannelForumThreadReply(
    val id: IntGenericId,
    @SerialName("content") val content: RawMessageContent,
    val repliesTo: IntGenericId,
    val editedAt: Instant?,
    val createdAt: Instant,
    val createdBy: GenericId
)