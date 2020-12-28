package otus.amogilevskiy.core

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RequestMapping("/api/1.0/users")
@RestController
class UserController(private val userService: UserService) {

    @PostMapping()
    fun createUser(@RequestBody user: User) = userService.createUser(user)

    @GetMapping
    fun getUsers(): List<User> = userService.getUsers()

    @GetMapping(
        value = ["/{id}"]
    )
    fun getUser(@PathVariable id: Long): User {
        return userService.getUser(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PatchMapping(
        value = ["/{id}"]
    )
    fun updateUser(@PathVariable id: Long, @RequestBody dto: UpdateUserDto): User {
        return userService.updateUser(id, dto) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping(
        value = ["/{id}"]
    )
    fun deleteUser(@PathVariable id: Long): User {
        return userService.deleteUser(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}