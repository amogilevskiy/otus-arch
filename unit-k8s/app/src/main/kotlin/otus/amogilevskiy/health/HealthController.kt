package otus.amogilevskiy.health

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/health")
@RestController
@EnableAutoConfiguration
class HealthController {

    @GetMapping
    fun get(): HealthResponse = HealthResponse()

}

