package controllers

import extensions.roundTo
import kotlinx.coroutines.*
import models.*
import mu.KotlinLogging
import java.io.File
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

object R2D2Controller {
    private lateinit var mediciones: List<Medicion>
    private lateinit var informe: Informe

    // Hereda el contexto de ejecución de la función que lo llama
    suspend fun loadData() = coroutineScope {
        logger.debug { "Cargando datos..." }
        try {
            // Puedes hacerlo con Future o corrutinas.., pero esto es Kotlin Style ;)
            // Creo un scope para poder usar corutinas usando el Dispacher IO
            val myScope = CoroutineScope(Dispatchers.IO)

            // Lanzo en paralelo las dos tareas de carga de datos con asynchronously/await
            val listaCsvAsync = myScope.async {
                Medicion.loadFromCsvFile(File("data/data03.csv"))
            }
            val listaXmlAsync = myScope.async {
                Medicion.loadFromXmlFile(File("data/data03.xml"))
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
            logger.debug { "Datos totales: ${mediciones.size}" }
        } catch (e: Exception) {
            System.err.println("Error al cargar los datos: ${e.message}")
            exitProcess(1)
        }
        logger.debug { "Datos cargados exitosamente" }
    }

    suspend fun proccessData() = coroutineScope {
        val PAGE_SIZE = 25
        logger.debug { "Procesando datos..." }
        // De las mediciones debemos procesarlas en grupos de 25
        // Para cada grupo de 25 debemos calcular las estadísticas

        // Una lista de asyncs para poder esperar a que terminen todas
        val myScope = CoroutineScope(Dispatchers.Default)
        // Mi lista de asyncs para poder esperar a que terminen todas
        val estadisticasAsync = mutableListOf<Deferred<Estadistica>>()
        mediciones.windowed(PAGE_SIZE, PAGE_SIZE, true) { window ->
            estadisticasAsync.add(myScope.async { calcularEstadisticasWindow(window) })
        }
        // Espero a que terminen todas las tareas
        val estadisticas = estadisticasAsync.awaitAll()
        // Creo el informe con las estadísticas
        informe = Informe(estadisticas)
        logger.debug { "Informe de resumen estadístico realizado exitosamente" }
        logger.debug { "Datos procesados exitosamente" }
    }

    private suspend fun calcularEstadisticasWindow(mediciones: List<Medicion>): Estadistica = coroutineScope {
        // Corrutinas!!! o promesas!!! una para cada estadística
        logger.debug { "Calculando estadísticas..." }
        val myScope = CoroutineScope(Dispatchers.Default)
        // NO2
        val NO2Async = myScope.async {
            logger.debug { "Calculando NO2..." }
            Resumen(
                tipo = "NO2",
                minValue = mediciones.minBy { it.NO2 }.NO2,
                minDate = mediciones.minBy { it.NO2 }.fecha,
                maxValue = mediciones.maxBy { it.NO2 }.NO2,
                maxDate = mediciones.maxBy { it.NO2 }.fecha,
                avgValue = mediciones.map { it.NO2 }.average().roundTo(4)
            )
        }
        // Temperatura
        val temperaturaAsync = myScope.async {
            logger.debug { "Calculando temperatura..." }
            Resumen(
                tipo = "temperatura",
                minValue = mediciones.minBy { it.temperatura }.temperatura,
                minDate = mediciones.minBy { it.temperatura }.fecha,
                maxValue = mediciones.maxBy { it.temperatura }.temperatura,
                maxDate = mediciones.maxBy { it.temperatura }.fecha,
                avgValue = mediciones.map { it.temperatura }.average().roundTo(4)
            )
        }
        // CO
        val COAsync = myScope.async {
            logger.debug { "Calculando CO..." }
            Resumen(
                tipo = "CO",
                minValue = mediciones.minBy { it.CO }.CO,
                minDate = mediciones.minBy { it.CO }.fecha,
                maxValue = mediciones.maxBy { it.CO }.CO,
                maxDate = mediciones.maxBy { it.CO }.fecha,
                avgValue = mediciones.map { it.CO }.average().roundTo(4)
            )
        }
        // Ozone
        val OzoneAsync = myScope.async {
            logger.debug { "Calculando estadísticas..." }
            Resumen(
                tipo = "Ozone",
                minValue = mediciones.minBy { it.ozone }.ozone,
                minDate = mediciones.minBy { it.ozone }.fecha,
                maxValue = mediciones.maxBy { it.ozone }.ozone,
                maxDate = mediciones.maxBy { it.ozone }.fecha,
                avgValue = mediciones.map { it.ozone }.average().roundTo(4)
            )
        }

        logger.debug { "Estadísticas calculadas exitosamente" }
        return@coroutineScope Estadistica(
            resumenes = mapOf(
                "NO2" to NO2Async.await(),
                "CO" to COAsync.await(),
                "Ozone" to OzoneAsync.await(),
                "Temperatura" to temperaturaAsync.await()
            )
        )

    }

    suspend fun saveData() = coroutineScope {
        logger.debug { "Salvando datos..." }
        val myScope = CoroutineScope(Dispatchers.IO)
        // Guardar el informe en un archivo
        val informeJson = myScope.launch {
            logger.debug { "Guardando informe en JSON..." }
            Informe.writeToJsonFile(informe, File("data/informe.json"))
        }

        informeJson.join()

        logger.debug { "Datos salvados exitosamente" }
    }

}
