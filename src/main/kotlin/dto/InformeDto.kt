package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
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
data class InformeDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    @XmlElement(true)
    val estadisticas: List<EstadisticaDto>,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object
}

fun InformeDto.Companion.writeToXmlFile(informe: InformeDto, xmlFile: File) {
    logger.debug { "Escribiendo fichero XML ${xmlFile.absolutePath}..." }
    val xml = XML {
        indentString = "  "
        autoPolymorphic = true
    }
    xmlFile.writeText(xml.encodeToString(informe))
    logger.debug { "Fichero XML ${xmlFile.absolutePath} escrito exitosamente." }
}

