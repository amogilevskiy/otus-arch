package otus.amogilevskiy.order

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/1.0/orders")
@RestController
class OrderController(private val orderService: OrderService) {

    @PostMapping()
    fun createOrder(@RequestHeader("If-Match") ifMatch: String?, @RequestBody order: Order) =
        orderService.createOrder(order, ifMatch)

    @GetMapping
    fun getOrders(): ResponseEntity<List<Order>> {
        val orders = orderService.getOrders()
        return ResponseEntity.ok().eTag(orders.size.toString()).body(orders)
    }

}