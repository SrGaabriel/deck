package com.deck.core.stateless

import com.deck.common.content.node.Node
import com.deck.common.entity.RawMentionType
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.Mentionable
import com.deck.core.entity.Role
import com.deck.core.util.BlankStatelessTeam
import kotlinx.serialization.json.JsonPrimitive

public interface StatelessRole: StatelessEntity<Role>, Mentionable {
    public val id: IntGenericId
    public val teamId: GenericId

    public val team: StatelessTeam get() = BlankStatelessTeam(client, teamId)

    override fun getMentionNode(): Node = Node.Mention(JsonPrimitive(id), RawMentionType.ROLE)

    override suspend fun getState(): Role {
        TODO("Not yet implemented")
    }
}