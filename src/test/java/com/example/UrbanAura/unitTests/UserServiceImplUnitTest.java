package com.example.UrbanAura;

import com.example.UrbanAura.exceptions.AlreadyExistsException;
import com.example.UrbanAura.exceptions.ResourceNotFoundException;
import com.example.UrbanAura.models.dtos.UserDetailsDTO;
import com.example.UrbanAura.models.dtos.UserUpdateUsernameDTO;
import com.example.UrbanAura.models.entities.Role;
import com.example.UrbanAura.models.entities.User;
import com.example.UrbanAura.repositories.RoleRepository;
import com.example.UrbanAura.repositories.UserRepository;
import com.example.UrbanAura.requests.CreateUserRequest;
import com.example.UrbanAura.requests.UserUpdateRequest;
import com.example.UrbanAura.services.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Role role;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(userId);


        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals("John", foundUser.getFirstName());
        assertEquals("Doe", foundUser.getLastName());

    }


    @Test
    public void testCreateUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFirstName("John");
        request.setLastName("Doe");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(request.getEmail());
        savedUser.setPassword("hashedPassword"); // Ще симулираме хеширана парола
        savedUser.setFirstName(request.getFirstName());
        savedUser.setLastName(request.getLastName());
        Role defaultRole = new Role();
        defaultRole.setName("ROLE_USER");
        savedUser.setRoles(Set.of(defaultRole));

        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);

        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(defaultRole));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");
        User result = userService.createUser(request);
        assertNotNull(result);
        assertEquals(request.getEmail(), result.getEmail());
        assertEquals("hashedPassword", result.getPassword());
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        Assertions.assertTrue(result.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_USER")));

    }

    @Test
    public void testUpdateUser() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        User result = userService.updateUser(request, userId);
        assertNotNull(result); //Резултатът не е null: това означава, че методът е върнал валиден обект.
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(userId, result.getId());

    }


    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("testUser");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Authentication authentication = new TestingAuthenticationToken(
                "adminUser",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));

        // Act (изпълняваме тестваната функция)
        userService.deleteUser(userId, null);

        // Assert (проверяваме резултата)
        Mockito.verify(userRepository).deleteById(userId);
    }


    @Test
    public void testCreateUser_AlreadyExists_ThrowsException() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@example.com");
        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        Exception exception = Assertions.assertThrows(AlreadyExistsException.class, () -> {
            userService.createUser(request);
        });
        assertEquals("Oops! " + request.getEmail() + " already exists.", exception.getMessage());

    }


    @Test
    public void testConvertUserToDto() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        UserDetailsDTO expectedDto = new UserDetailsDTO();
        expectedDto.setEmail("test@example.com");
        expectedDto.setFirstName("John");
        expectedDto.setLastName("Doe");

        Mockito.when(modelMapper.map(user, UserDetailsDTO.class)).thenReturn(expectedDto);

        // Act
        UserDetailsDTO result = userService.convertUserToDto(user);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }
    @Test
    public void testConvertUserToDto_NullUser() {
        UserDetailsDTO result = userService.convertUserToDto(null);
        assertNull(result, "For null input, the result should be null");
    }
    @Test
    public void testConvertUserUpdateToDto_NullUser() {
        UserUpdateUsernameDTO result = userService.convertUserUpdateToDto(null);
        assertNull(result, "For null input, the result should be null");
    }


    @Test
    public void testConvertUserUpdateToDto() {
        // Arrange
        User user = new User();
        user.setId(2L);
        user.setFirstName("newUsername");

        UserUpdateUsernameDTO expectedDto = new UserUpdateUsernameDTO();
        expectedDto.setFirstName("newUsername");

        Mockito.when(modelMapper.map(user, UserUpdateUsernameDTO.class)).thenReturn(expectedDto);

        // Act
        UserUpdateUsernameDTO result = userService.convertUserUpdateToDto(user);

        // Assert
        assertNotNull(result);
        assertEquals("newUsername", result.getFirstName());
    }
    @Test
    public void testGetAuthenticatedUser() {
        // Arrange
        String email = "loggedUser@example.com";
        User expectedUser = new User();
        expectedUser.setEmail(email);

        Authentication authentication = new TestingAuthenticationToken(email, null);
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));

        Mockito.when(userRepository.findByEmail(email)).thenReturn(expectedUser);

        // Act
        User result = userService.getAuthenticatedUser();

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testGetUserById_UserNotFound_ThrowsException() {
        Long userId = 1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("User not found", exception.getMessage());


    }
}
