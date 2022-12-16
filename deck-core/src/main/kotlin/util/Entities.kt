package io.github.srgaabriel.deck.core.util

import io.github.srgaabriel.deck.common.EmbedBuilder
import io.github.srgaabriel.deck.core.entity.Message
import io.github.srgaabriel.deck.core.entity.impl.DeckMessage
import io.github.srgaabriel.deck.core.stateless.StatelessGroup
import io.github.srgaabriel.deck.core.stateless.StatelessMember
import io.github.srgaabriel.deck.core.stateless.StatelessMessage
import io.github.srgaabriel.deck.core.stateless.StatelessRole
import io.github.srgaabriel.deck.core.stateless.channel.StatelessMessageChannel

/**
 * Adds the specified member to this group
 *
 * @param member member to be added
 */
public suspend fun StatelessGroup.addMember(member: StatelessMember): Unit =
    addMember(member.id)

/**
 * Removes the specified member from this group
 *
 * @param member member to be removed
 */
public suspend fun StatelessGroup.removeMember(member: StatelessMember): Unit =
    removeMember(member.id)

/**
 * Assigns the specified role to this member
 *
 * @param role role to be assigned to this member
 */
public suspend fun StatelessMember.addRole(role: StatelessRole): Unit =
    addRole(role.id)

/**
 * Removes the specified role from this member
 *
 * @param role role to be removed from this member
 */
public suspend fun StatelessMember.removeRole(role: StatelessRole): Unit =
    removeRole(role.id)

/**
 * Overwrites this message's written content
 *
 * @param content new content
 * @return new message with the new content
 */
public suspend fun StatelessMessage.update(content: String): Message =
    DeckMessage.from(client, client.rest.channel.updateMessage(channelId, id) {
        this.content = content
    })

/**
 * Sends a reply of content [content] to this message
 *
 * @param content plain Markdown text
 * @return the just sent message
 */
public suspend fun StatelessMessage.sendReply(content: String): Message = sendReply {
    this.content = content
}

/**
 * Sends a reply containing an embed and possibly (if not null), a written content [content].
 *
 * @param content additional content, null if not needed
 * @param builder the embed builder
 *
 * @return the just sent message
 */
public suspend fun StatelessMessage.sendReplyWithEmbed(content: String? = null, builder: EmbedBuilder.() -> Unit): Message = sendReply {
    this.content = content
    embed(builder)
}

/**
 * Sends a message on this channel of written content [content].
 *
 * @param content plain markdown content
 *
 * @return the just sent message
 */
public suspend fun StatelessMessageChannel.sendMessage(content: String): Message = sendMessage {
    this.content = content
}

/**
 * Sends a message containing an embed and possibly (if not null), a written content [content].
 *
 * @param content additional plain markdown content, null if not needed
 * @param builder the embed builder
 *
 * @return the just sent message
 */
public suspend fun StatelessMessageChannel.sendEmbed(content: String? = null, builder: EmbedBuilder.() -> Unit): Message = sendMessage {
    this.content = content
    embed(builder)
}