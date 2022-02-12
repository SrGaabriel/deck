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

/**
 * Accesses and reads the specified URL,
 * then uploads the read bytes, also closing the stream.
 *
 * @param url image url
 */
public suspend fun DeckClient.uploadFile(
    url: String,
    type: DynamicMediaType
): GuildedMedia = coroutineScope {
    async(Dispatchers.IO) {
        val connection = URL(url).openConnection().also { it.connect() }
        connection.getInputStream().let { stream ->
            uploadFile(connection.url.file.ifEmpty { "image" }, stream, type).also {
                stream.close()
            }
        }
    }
}.await()

/**
 * Reads the file's bytes and uploads it
 * to guilded's cdn.
 *
 * @param file image
 */
public suspend fun DeckClient.uploadFile(
    file: File,
    type: DynamicMediaType
): GuildedMedia = uploadFile(file.name, file.readBytes(), type)

/**
 * Buffers and reads the [stream], without closing it.
 * It's the user responsibility to close it.
 *
 * @param name the file name with the extension
 * @param stream
 */
public suspend fun DeckClient.uploadFile(
    name: String,
    stream: InputStream,
    type: DynamicMediaType
): GuildedMedia = uploadFile(name, stream.readBytes(), type)

/**
 * Uploads the specified [byteArray] as an image, returning
 * a [GuildedMedia] with the cdn's reference.
 *
 * @param name the file name with the extension
 * @param byteArray the bytes that'll be uploaded
 */
public suspend fun DeckClient.uploadFile(
    name: String,
    byteArray: ByteArray,
    type: DynamicMediaType
): GuildedMedia = rest.mediaRoute.uploadMedia(name, byteArray, type)