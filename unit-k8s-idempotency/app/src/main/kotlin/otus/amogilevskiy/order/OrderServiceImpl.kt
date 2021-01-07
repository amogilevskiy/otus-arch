package otus.amogilevskiy.order

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import otus.amogilevskiy.util.ETagValueConverter

@Service
class OrderServiceImpl(private val orderRepository: OrderRepository, private val eTagValueParser: ETagValueConverter) :
    OrderService {

    override fun createOrder(order: Order, version: String?): Order {
        val count = orderRepository.count()
        version?.let {
            if (eTagValueParser.parse(it).toLong() == count) {
                return orderRepository.save(order)
            }
        }
        throw ResponseStatusException(HttpStatus.CONFLICT)
    }

    override fun getOrders(): List<Order> = orderRepository.findAll()

}