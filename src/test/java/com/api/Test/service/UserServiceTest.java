package com.api.Test.service;

import com.api.Test.dto.UserDto;
import com.api.Test.entity.UserEntity;
import com.api.Test.repository.UserRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findUserByUsernameMockTest() {
        assertThat(userService).isNotNull();

        //Given

        UserEntity mockUser = new UserEntity();
        mockUser.setId(1L);
        mockUser.setUsername("Sultan");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("1234");
        mockUser.setCreateAt(LocalDateTime.now());
        mockUser.setUpdateAt(LocalDateTime.now());

        // when
        when(this.userRepository.findByUsername("Sultan"))
                .thenReturn(Optional.of(mockUser));

        Optional<UserDto> result = this.userService.findUserByUsername("Sultan");

        //then
        assertThat(result).isPresent();
    }

    @Test
    void createUserMockTest() {
        //Given
        var userEntity = UserEntity.builder()
                .id(1L)
                .username("Jhonattan")
                .email("Jhonatan@gmail.com")
                .password("1212")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        var userDto = UserDto.builder()
                .username("Jhonattan")
                .email("Jhonatan@gmail.com")
                .password("1212")
                .createAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();

        // when

        when(this.userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
        UserDto savedUser = this.userService.create(userDto);

        // then

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("Jhonattan");
        assertThat(savedUser.getEmail()).isEqualTo("Jhonatan@gmail.com");
    }

}