package otus.amogilevskiy.core

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: User): User = userRepository.save(user)

    fun getUsers(): List<User> = userRepository.findAll()

    fun getUser(id: Long): User? = userRepository.findByIdOrNull(id)

    fun updateUser(id: Long, dto: UpdateUserDto): User? {
        return userRepository.findByIdOrNull(id)?.apply {
            dto.firstName?.let {
                firstName = it
            }

            dto.lastName?.let {
                lastName = it
            }

            dto.email?.let {
                email = it
            }
        }?.let {
            userRepository.saveAndFlush(it)
        }
    }

    fun deleteUser(id: Long): User? {
        return userRepository.findByIdOrNull(id)?.apply {
            userRepository.deleteById(id)
        }
    }

}