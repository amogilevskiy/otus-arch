package otus.amogilevskiy.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
}
