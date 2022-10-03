package models

import extensions.parseZonedDateTime
import mu.KotlinLogging
import org.jdom2.Document
import org.jdom2.Element
import org.jdom2.input.SAXBuilder
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDateTime

/**
 * Medicion
 */

private val logger = KotlinLogging.logger {}

data class Medicion(
    val id: Long = System.currentTimeMillis(),
    val fecha: LocalDateTime = LocalDateTime.now(),
    val tipo: String = "",
    val NO2: Double = 0.0,
    val temperatura: Double = 0.0,
    val CO: Double = 0.0,
    val ozone: Double = 0.0,
) {
    companion object
}

// funciones de extensión para no tocar el data, esto es solo un criterio de diseño
// Lectura del fichero CSV
fun Medicion.Companion.loadFromCsvFile(csvFile: File): List<Medicion> {
    logger.debug { "Cargando datos desde ${csvFile.absolutePath}..." }
    // Early return --- Aprender a usarlo :)
    if (!csvFile.exists()) {
        logger.error { "File ${csvFile.absolutePath} does not exist." }
        throw FileNotFoundException("Fichero ${csvFile.absolutePath} no existe.")
    }
    return csvFile.readLines().drop(1)
        .map { line -> line.split(",") }
        .map { fields ->
            Medicion(
                id = fields[0].toLong(),
                fecha = fields[1].parseZonedDateTime(),
                tipo = fields[2],
                NO2 = fields[5].toDouble(),
                ozone = fields[6].toDouble(),
                temperatura = fields[9].toDouble(),
                CO = fields[10].toDouble(),
            )
        }
}

fun Medicion.Companion.loadFromXmlFile(xmlFile: File): List<Medicion> {
    logger.debug { "Cargando datos desde ${xmlFile.absolutePath}..." }
    // Early return --- Aprender a usarlo :)
    if (!xmlFile.exists()) {
        logger.error { "File ${xmlFile.absolutePath} does not exist." }
        throw FileNotFoundException("Fichero ${xmlFile.absolutePath} no existe.")
    }
    val sax = SAXBuilder()
    val doc: Document = sax.build(xmlFile)
    return doc.rootElement.getChild("resources")
        .getChildren("resource")
        .map { it.toDto() }

}

private fun Element.toDto(): Medicion {
    /*        <resource>
    <str name="ayto:particles"/>
    <str name="ayto:NO2">116.0</str>
    <str name="ayto:type">AirQualityObserved</str>
    <str name="ayto:latitude">43.449</str>
    <str name="ayto:temperature">22.9</str>
    <str name="ayto:altitude"/>
    <str name="ayto:speed"/>
    <str name="ayto:CO">0.1</str>
    <date name="dc:modified">2021-08-27T07:47:39Z</date>
    <str name="dc:identifier">3047</str>
    <str name="ayto:longitude">-3.8677</str>
    <str name="ayto:odometer"/>
    <str name="ayto:course"/>
    <str name="ayto:ozone">120.0</str>
    <str name="uri">http://datos.santander.es/api/datos/sensores_smart_mobile/3047.xml</str>
    </resource>*/
    val strs = this.getChildren("str")
    return Medicion(
        NO2 = strs[1].text.toDouble(),
        tipo = strs[2].text,
        temperatura = strs[4].text.toDouble(),
        CO = strs[7].text.toDouble(),
        id = strs[8].text.toLong(),
        ozone = strs[12].text.toDouble(),
        fecha = getChild("date").value.parseZonedDateTime()
    )
}


