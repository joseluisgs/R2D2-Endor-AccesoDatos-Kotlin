package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import serializers.LocalDateTimeSerializer
import serializers.UUIDSerializer
import java.time.LocalDateTime
import java.util.*

@Serializable
@SerialName("estadistica")
data class Estadistica(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    @XmlElement(true)
    val resumenes: List<Resumen> = listOf(),
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)