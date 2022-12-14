package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlElement
import serializers.LocalDateTimeSerializer
import serializers.UUIDSerializer
import java.io.File
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger {}

@Serializable
@SerialName("informe")
data class Informe(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    @XmlElement(true)
    val estadisticas: List<Estadistica>,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object
}

fun Informe.Companion.writeToJsonFile(informe: Informe, jsonFile: File) {
    logger.debug { "Escribiendo fichero JSON ${jsonFile.absolutePath}..." }
    val json = Json { prettyPrint = true }
    jsonFile.writeText(json.encodeToString(informe))
    logger.debug { "Fichero JSON ${jsonFile.absolutePath} escrito exitosamente." }
}

fun Informe.Companion.writeToXmlFile(informe: Informe, xmlFile: File) {
    logger.debug { "Escribiendo fichero XML ${xmlFile.absolutePath}..." }
    val xml = XML { indentString = "  " }
    xmlFile.writeText(xml.encodeToString(informe))
    logger.debug { "Fichero XML ${xmlFile.absolutePath} escrito exitosamente." }
}

