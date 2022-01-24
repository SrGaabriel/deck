package com.deck.core.util

import com.deck.common.util.DynamicMediaType
import com.deck.common.util.GuildedMedia
import com.deck.core.DeckClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.InputStream
import java.net.URL

public suspend fun DeckClient.uploadFile(
    url: String,
    type: DynamicMediaType
): GuildedMedia = coroutineScope {
    async(Dispatchers.IO) {
        val connection = URL(url).openConnection().also { it.connect() }
        uploadFile(connection.getInputStream(), type)
    }
}.await()

public suspend fun DeckClient.uploadFile(
    file: File,
    type: DynamicMediaType
): GuildedMedia = uploadFile(file.readBytes(), type)

public suspend fun DeckClient.uploadFile(
    stream: InputStream,
    type: DynamicMediaType
): GuildedMedia = uploadFile(stream.readBytes(), type)

public suspend fun DeckClient.uploadFile(
    byteArray: ByteArray,
    type: DynamicMediaType
): GuildedMedia = rest.mediaRoute.uploadMedia(byteArray, type)