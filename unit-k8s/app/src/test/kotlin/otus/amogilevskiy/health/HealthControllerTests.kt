package otus.amogilevskiy.health

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthControllerTests {

    @Autowired
    lateinit var client: TestRestTemplate

    @Test
    fun `it returns OK status`() {
        val result = client.getForEntity("/health", HealthResponse::class.java)

        Assertions.assertEquals(HealthResponse.OK, result.body?.status)
        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
    }

}