package otus.amogilevskiy

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import otus.amogilevskiy.core.UpdateUserDto
import otus.amogilevskiy.core.User
import otus.amogilevskiy.core.UserList
import otus.amogilevskiy.core.UserRepository

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTests : ContainerBaseTests() {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var client: TestRestTemplate

    @BeforeEach
    fun setupRestTemplate() {
        client.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
    }

    @BeforeEach
    fun resetRepository() {
        userRepository.deleteAll()
    }

    @Test
    fun `it returns list of all users`() {
        userRepository.saveAll(users())

        val response = client.getForEntity(usersUrl(), UserList::class.java)

        assertEquals(users().size, response.body?.size)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `it returns an existing user`() {
        val user = userRepository.save(user())

        val response = client.getForEntity(usersUrl(user.id), User::class.java)
        val data = response.body

        assertEquals(user.firstName, data?.firstName)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `it returns a not found status code`() {
        val response = client.getForEntity(usersUrl(1), String::class.java)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun `it creates a new user`() {
        val entity = HttpEntity(user())

        val response = client.postForEntity(usersUrl(), entity, User::class.java)
        val data = response.body

        assertEquals(user().firstName, data?.firstName)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(data?.id)
    }

    @Test
    fun `it partially updates an existing user`() {
        val user = userRepository.save(user())

        val dto = UpdateUserDto(firstName = "Updated name", email = "Updated email")
        val entity = HttpEntity(dto)
        val response =
            client.exchange(
                usersUrl(user.id),
                HttpMethod.PATCH,
                entity,
                User::class.java
            )
        val data = response.body

        assertEquals(dto.firstName, data?.firstName)
        assertEquals(dto.email, data?.email)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `it deletes an existing user`() {
        val user = userRepository.save(user())

        val response = client.exchange(
            usersUrl(user.id),
            HttpMethod.DELETE,
            HttpEntity.EMPTY,
            User::class.java
        )
        val data = response.body

        assertEquals(user.firstName, data?.firstName)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    private fun user() = User(firstName = "Test name", lastName = "Test last name", email = "test@example.com")

    private fun users() = listOf(user())

    private fun usersUrl(id: Long? = null): String {
        val path = "/api/1.0/users"
        if (id != null) {
            return "$path/$id"
        }
        return path
    }
}