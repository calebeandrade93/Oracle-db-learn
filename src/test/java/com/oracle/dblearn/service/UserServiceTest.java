package com.oracle.dblearn.service;

import com.oracle.dblearn.exception.EmailAlreadyExistsException;
import com.oracle.dblearn.exception.InvalidPasswordException;
import com.oracle.dblearn.infra.utils.PasswordChecker;
import com.oracle.dblearn.model.entity.User;
import com.oracle.dblearn.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {
        // Arrange
        User user = new User();
        user.setName("Calebe");
        user.setEmail("calebe@example.com");
        user.setPassword("SenhaValida123!@");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        User user = new User();
        user.setName("Calebe");
        user.setEmail("calebe@example.com");
        user.setPassword("SenhaValida123!@");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        User user = new User();
        user.setName("Calebe");
        user.setEmail("calebe@example.com");
        user.setPassword("Senha");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> {
            userService.createUser(user);
        });
    }
}
