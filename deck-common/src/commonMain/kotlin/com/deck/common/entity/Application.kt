package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.Timestamp
import kotlinx.serialization.Serializable

@Serializable
public data class RawApplication(
    val id: IntGenericId,
    val status: Int,
    // val applicantSocialLinks: List<*>,
    // val applicantAliases: List<*>,
    val teamId: GenericId,
    val gameId: IntGenericId?,
    val createdAt: Timestamp,
    val createdBy: GenericId,
    val applicantName: String,
    val applicantProfilePicture: String? = null,
    val applicantProfileBannerBlur: String? = null,
    val formResult: RawApplicationFormResult
)

@Serializable
public data class RawApplicationFormResult(
    val isValid: Boolean,
    val sections: List<RawApplicationSection>,
    val hasChanged: Boolean
)

@Serializable
public data class RawApplicationSection(
    val header: String,
    val fieldSpecs: List<RawApplicationSectionField>
)

@Serializable
public data class RawApplicationSectionField(
    val grow: Int,
    val size: String,
    val type: String,
    val label: String,
    val fieldName: String,
    val isOptional: Boolean,
    val placeholder: String?,
    val defaultValue: RawMessageContent
)