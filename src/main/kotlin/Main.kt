import controllers.R2D2Controller
import kotlin.system.measureTimeMillis

suspend fun main() {
    println("R2D2 en Endor")

    measureTimeMillis {
        //runBlocking {
        R2D2Controller.loadData()

        R2D2Controller.proccessData()

        // R2D2Controller.saveData()
        //}

    }.also { println("Tiempo de procesamiento: ${it}ms") }
}