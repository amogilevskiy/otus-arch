package otus.amogilevskiy

import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthControllerTests : ContainerBaseTests() {

    @LocalManagementPort
    private var localManagementPort = 0

    @Autowired
    lateinit var client: TestRestTemplate

    @Test
    fun `it returns OK status`() {
        val response =
            client.getForEntity("http://localhost:${localManagementPort}/actuator/health", Map::class.java)
        Assert.assertEquals(HttpStatus.OK, response.statusCode)
    }

}