package extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun String.parseZonedDateTime(): LocalDateTime {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    return LocalDateTime.parse(this, formatter)
}