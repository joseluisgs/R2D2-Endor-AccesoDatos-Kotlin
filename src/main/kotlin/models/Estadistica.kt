package models

import java.time.LocalDateTime
import java.util.*

data class Estadistica(
    val id: UUID = UUID.randomUUID(),
    val fecha: LocalDateTime = LocalDateTime.now(),
    val NO2: Resumen = Resumen(tipo = "NO2"),
    val temperatura: Resumen = Resumen(tipo = "Temperatura"),
    val CO: Resumen = Resumen(tipo = "CO"),
    val ozone: Resumen = Resumen(tipo = "Ozone"),
)