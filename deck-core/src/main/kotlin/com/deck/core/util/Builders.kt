package com.deck.core.util

import com.deck.common.util.mapToModel
import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.builder.DeckModifySelfBuilder
import com.deck.core.entity.Channel
import com.deck.core.entity.SelfUser
import com.deck.rest.request.ModifySelfUserRequest
import com.deck.rest.request.SendMessageRequest
import com.deck.rest.request.SendMessageResponse
import com.deck.rest.route.ChannelRoute
import com.deck.rest.route.UserRoute
import io.ktor.http.*

suspend fun UserRoute.editSelf(self: SelfUser, builder: DeckModifySelfBuilder.() -> Unit) =
    sendRequest<Unit, ModifySelfUserRequest>(
        endpoint = "/users/${self.id}/profilev2",
        method = HttpMethod.Put,
        body = DeckModifySelfBuilder().apply(builder).toRequest()
    )

suspend fun ChannelRoute.sendMessage(channel: Channel, builder: DeckMessageBuilder.() -> Unit) =
    sendRequest<SendMessageResponse, SendMessageRequest>(
        endpoint = "/channels/${channel.id.mapToModel()}/messages",
        method = HttpMethod.Post,
        body = DeckMessageBuilder().apply(builder).toRequest()
    )
