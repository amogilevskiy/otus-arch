package otus.amogilevskiy

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import otus.amogilevskiy.order.Order
import otus.amogilevskiy.order.OrderList
import otus.amogilevskiy.order.OrderRepository
import otus.amogilevskiy.util.ETagValueConverter
import kotlin.random.Random

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTests : ContainerBaseTests() {

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var client: TestRestTemplate

    @Autowired
    lateinit var eTagValueConverter: ETagValueConverter

    @BeforeEach
    fun setupRestTemplate() {
        client.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
    }

    @BeforeEach
    fun resetRepository() {
        orderRepository.deleteAll()
    }

    @Test
    fun `it returns list of all orders`() {
        val orders = orders()

        orderRepository.saveAll(orders)

        val response = client.getForEntity(ordersUrl(), OrderList::class.java)

        val actualTagValue = eTagValueConverter.parse(response.headers.eTag!!)

        assertEquals(orders.size.toString(), actualTagValue)
        assertEquals(orders.size, response.body?.size)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `it creates a new order`() {
        val order = order()

        repeat(Random.nextInt(10)) {
            orderRepository.save(order)
        }

        val count = orderRepository.count()

        val headers = HttpHeaders()
        headers.ifMatch = listOf(eTagValueConverter.wrap(count.toString()))

        val entity = HttpEntity(order, headers)

        val response = client.postForEntity(ordersUrl(), entity, Order::class.java)
        val data = response.body

        assertEquals(order, data)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(data?.id)
    }

    @Test
    fun `it returns conflict error when ETag is wrong or absent`() {
        val order = order()

        val entity = HttpEntity(order)

        val response = client.postForEntity(ordersUrl(), entity, String::class.java)
        val data = response.body
        val actualStatusCode = response.statusCode

        assertEquals(HttpStatus.CONFLICT, actualStatusCode)
    }

    private fun order() = Order(content = "Some stuff", address = "Default city", comment = "Gift")

    private fun orders() = listOf(order())

    private fun ordersUrl(id: Long? = null): String {
        val path = "/api/1.0/orders"
        if (id != null) {
            return "$path/$id"
        }
        return path
    }
}