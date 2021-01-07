package otus.amogilevskiy.order

interface OrderService {

    fun createOrder(order: Order, version: String?): Order

    fun getOrders(): List<Order>

}