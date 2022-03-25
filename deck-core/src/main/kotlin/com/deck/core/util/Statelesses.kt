package com.deck.core.util

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessDocumentationChannel
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.stateless.channel.StatelessListChannel
import com.deck.core.stateless.channel.StatelessMessageChannel
import java.util.*

public fun StatelessMessage(
    client: DeckClient,
    id: UUID,
    channelId: UUID,
    serverId: GenericId?
): StatelessMessage = BlankStatelessMessage(client, id, channelId, serverId)

internal data class BlankStatelessMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val channelId: UUID,
    override val serverId: GenericId?
): StatelessMessage

public fun StatelessMessageChannel(
    client: DeckClient,
    id: UUID,
    serverId: GenericId?
): StatelessMessageChannel = BlankStatelessMessageChannel(client, id, serverId)

internal data class BlankStatelessMessageChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId?
): StatelessMessageChannel

public fun StatelessDocumentationChannel(
    client: DeckClient,
    id: UUID,
    serverId: GenericId
): StatelessDocumentationChannel = BlankStatelessDocumentationChannel(client, id, serverId)

internal data class BlankStatelessDocumentationChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId
): StatelessDocumentationChannel

public fun StatelessListChannel(
    client: DeckClient,
    id: UUID,
    serverId: GenericId
): StatelessListChannel = BlankStatelessListChannel(client, id, serverId)

internal data class BlankStatelessListChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId
): StatelessListChannel

public fun StatelessForumChannel(
    client: DeckClient,
    id: UUID,
    serverId: GenericId
): StatelessForumChannel = BlankStatelessForumChannel(client, id, serverId)

internal class BlankStatelessForumChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId
): StatelessForumChannel

public fun StatelessServer(
    client: DeckClient,
    id: GenericId,
): StatelessServer = BlankStatelessServer(client, id)

internal data class BlankStatelessServer(
    override val client: DeckClient,
    override val id: GenericId
): StatelessServer

public fun StatelessUser(
    client: DeckClient,
    id: GenericId,
): StatelessUser = BlankStatelessUser(client, id)

internal data class BlankStatelessUser(
    override val client: DeckClient,
    override val id: GenericId
): StatelessUser

public fun StatelessMember(
    client: DeckClient,
    id: GenericId,
    serverId: GenericId
): StatelessMember = BlankStatelessMember(client, id, serverId)

internal data class BlankStatelessMember(
    override val client: DeckClient,
    override val id: GenericId,
    override val serverId: GenericId
): StatelessMember