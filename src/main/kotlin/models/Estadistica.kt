package models

import kotlinx.serialization.Contextual
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
    @Serializable(with = LocalDateTimeSerializer::class)
    val fecha: LocalDateTime = LocalDateTime.now(),
    @XmlElement(true)
    @Contextual
    val resumenes: Map<String, Resumen> = mapOf(),
)