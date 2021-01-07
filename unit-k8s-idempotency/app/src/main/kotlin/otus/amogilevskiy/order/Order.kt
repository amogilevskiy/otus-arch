package otus.amogilevskiy.order

import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var content: String,
    var address: String,
    var comment: String?
)

class OrderList : MutableList<Order> by ArrayList()