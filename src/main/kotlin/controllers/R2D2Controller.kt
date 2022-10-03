package controllers

import dto.MedicionDto
import dto.loadFromCsvFile
import dto.loadFromXmlFile
import dto.toModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import models.Estadistica
import models.Medicion
import models.Resumen
import mu.KotlinLogging
import java.io.File
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

object R2D2Controller {
    private var mediciones = mutableListOf<Medicion>()

    // Hereda el contexto de ejecución de la función que lo llama
    suspend fun loadData() = coroutineScope {
        logger.debug { "Cargando datos..." }
        try {
            // Puedes hacerlo con Future o corrutinas.., pero esto es Kotlin Style ;)
            // Creo un scope para poder usar corutinas usando el Dispacher IO
            val myScope = CoroutineScope(Dispatchers.IO)

            // Lanzo en paralelo las dos tareas de carga de datos con asynchronously/await
            val listaCsvAsync = myScope.async {
                MedicionDto.loadFromCsvFile(File("data/data03.csv"))
                    .map { it.toModel() }
            }
            val listaXmlAsync = myScope.async {
                MedicionDto.loadFromXmlFile(File("data/data03.xml"))
                    .map { it.toModel() }
            }

            // Espero a que terminen las dos tareas y las guardo en una lista de mediciones
            val listaCsv = listaCsvAsync.await()
            val listaXml = listaXmlAsync.await()

            logger.debug { "Datos cargados CSV: ${listaCsv.size}" }
            logger.debug { "Datos cargados XML: ${listaXml.size}" }

            // Lista sin elementos repetidos, podría hacerlo como conjuntos
            mediciones = (listaCsv + listaXml) // unimos las dos listas
                .distinctBy { it.id } // Elimino los repetidos
                .sortedBy { it.id } // Ordeno por id
                .toMutableList()
            logger.debug { "Datos totales: ${mediciones.size}" }
        } catch (e: Exception) {
            System.err.println("Error al cargar los datos: ${e.message}")
            exitProcess(1)
        }
        logger.debug { "Datos cargados" }
    }

    fun proccessData() {
        val PAGE_SIZE = 25
        logger.debug { "Procesando datos..." }
        // De las mediciones debemos procesarlas en grupos de 25
        // Para cada grupo de 25 debemos calcular las estadísticas
        mediciones.windowed(PAGE_SIZE, PAGE_SIZE, true) { window ->
            logger.debug { "Calculando mediciones..." }
            val estadistica = calcularEstadisticasWindow(window)
            println(estadistica)
        }
        logger.debug { "Datos procesados" }
    }

    private fun calcularEstadisticasWindow(mediciones: List<Medicion>): Estadistica {
        // Corrutinas!!! o promesas!!! una para cada estadística
        // NO2
        val NO2 = Resumen(
            tipo = "NO2",
            minValue = mediciones.minBy { it.NO2 }.NO2,
            minDate = mediciones.minBy { it.NO2 }.fecha,
            maxValue = mediciones.maxBy { it.NO2 }.NO2,
            maxDate = mediciones.maxBy { it.NO2 }.fecha,
            avgValue = mediciones.map { it.NO2 }.average()
        )
        // Temperatura
        val temperatura = Resumen(
            tipo = "Temperatura",
            minValue = mediciones.minBy { it.temperatura }.temperatura,
            minDate = mediciones.minBy { it.temperatura }.fecha,
            maxValue = mediciones.maxBy { it.temperatura }.temperatura,
            maxDate = mediciones.maxBy { it.temperatura }.fecha,
            avgValue = mediciones.map { it.temperatura }.average()
        )
        // CO
        val CO = Resumen(
            tipo = "CO2",
            minValue = mediciones.minBy { it.CO }.CO,
            minDate = mediciones.minBy { it.CO }.fecha,
            maxValue = mediciones.maxBy { it.CO }.CO,
            maxDate = mediciones.maxBy { it.CO }.fecha,
            avgValue = mediciones.map { it.CO }.average()
        )
        // Ozone
        val OZONE = Resumen(
            tipo = "Ozone",
            minValue = mediciones.minBy { it.ozone }.ozone,
            minDate = mediciones.minBy { it.ozone }.fecha,
            maxValue = mediciones.maxBy { it.ozone }.ozone,
            maxDate = mediciones.maxBy { it.ozone }.fecha,
            avgValue = mediciones.map { it.ozone }.average()
        )

        return Estadistica(
            NO2 = NO2,
            temperatura = temperatura,
            CO = CO,
            ozone = OZONE
        )

    }

    fun saveData() {
        logger.debug { "Salvando datos.." }
        TODO("Not yet implemented")
        logger.debug { "Datos salvados" }
    }

}
