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
    channel: StatelessMessageChannel
): StatelessMessage = BlankStatelessMessage(client, id, channel)

internal data class BlankStatelessMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val channel: StatelessMessageChannel
): StatelessMessage

public fun StatelessMessageChannel(
    client: DeckClient,
    id: UUID,
    server: StatelessServer?
): StatelessMessageChannel = BlankStatelessMessageChannel(client, id, server)

internal data class BlankStatelessMessageChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val server: StatelessServer?
): StatelessMessageChannel

public fun StatelessDocumentationChannel(
    client: DeckClient,
    id: UUID,
    server: StatelessServer
): StatelessDocumentationChannel = BlankStatelessDocumentationChannel(client, id, server)

internal data class BlankStatelessDocumentationChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val server: StatelessServer
): StatelessDocumentationChannel

public fun StatelessListChannel(
    client: DeckClient,
    id: UUID,
    server: StatelessServer
): StatelessListChannel = BlankStatelessListChannel(client, id, server)

internal data class BlankStatelessListChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val server: StatelessServer
): StatelessListChannel

public fun StatelessForumChannel(
    client: DeckClient,
    id: UUID,
    server: StatelessServer
): StatelessForumChannel = BlankStatelessForumChannel(client, id, server)

internal class BlankStatelessForumChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val server: StatelessServer
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
    server: StatelessServer
): StatelessMember = BlankStatelessMember(client, id, server)

internal data class BlankStatelessMember(
    override val client: DeckClient,
    override val id: GenericId,
    override val server: StatelessServer
): StatelessMember