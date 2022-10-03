package models

import java.time.LocalDateTime
import java.util.*

data class Resumen(
    val id: UUID = UUID.randomUUID(),
    val tipo: String = "",
    val maxValue: Double = 0.0,
    val maxDate: LocalDateTime = LocalDateTime.now(),
    val minValue: Double = 0.0,
    val minDate: LocalDateTime = LocalDateTime.now(),
    val avgValue: Double = 0.0,
)
