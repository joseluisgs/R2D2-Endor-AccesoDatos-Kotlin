package models

import extensions.parseZonedDateTime
import mu.KotlinLogging
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
) 
