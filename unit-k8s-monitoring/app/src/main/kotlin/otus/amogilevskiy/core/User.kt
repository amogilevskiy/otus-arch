package otus.amogilevskiy.core

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var firstName: String,
    var lastName: String,
    var email: String
)

class UserList : MutableList<User> by ArrayList()