package com.guildedkt.builder

import com.guildedkt.mapToModel
import com.guildedkt.request.SendMessageRequest
import com.guildedkt.util.ContentBuilder
import java.util.*

class SendMessageRequestBuilder: RequestBuilder<SendMessageRequest> {
    var uniqueId: UUID = UUID.randomUUID()
    var text: String = ""
    var image: String? = null
    var private: Boolean = false
    var silent: Boolean = false

    fun content(contentBuilder: ContentBuilder.() -> Unit) = ContentBuilder().apply(contentBuilder).let {
        this.text = it.text
        this.image = it.image
    }

    override fun toRequest() = SendMessageRequest(
        messageId = uniqueId.mapToModel(),
        content = ContentBuilder().apply {
            text = this@SendMessageRequestBuilder.text
            image = this@SendMessageRequestBuilder.image
        }.toContent(),
        isPrivate = private,
        isSilent = silent
    )
}
