package otus.amogilevskiy.health

data class HealthResponse(val status: String) {

    constructor() : this(OK)

    companion object {
        val OK = "OK"
    }
}