package com.deck.core.stateless

import com.deck.common.content.node.Node
import com.deck.common.entity.RawMentionType
import com.deck.common.util.GenericId
import com.deck.common.util.Mentionable
import com.deck.core.entity.User
import kotlinx.serialization.json.JsonPrimitive

public interface StatelessUser: StatelessEntity<User>, Mentionable {
    public val id: GenericId

    override fun getMentionNode(): Node = Node.Mention(JsonPrimitive(id), RawMentionType.USER)

    override suspend fun getState(): User {
        return client.entityDelegator.getUser(id)
            ?: error("Tried to get the state of an invalid user.")
    }
}
