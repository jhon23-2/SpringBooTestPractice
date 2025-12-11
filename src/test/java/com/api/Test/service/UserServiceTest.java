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

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findUserByUsernameServiceMockTest() {
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
    void createUserServiceMockTest() {
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


    @Test
    void updateUserServiceMockTest(){
        // Given


        //MockUserEntity when userRepository.findById() mock is called -> (Mock User that exist to database)
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1L);
        mockUser.setUsername("Sultan");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("1234");
        mockUser.setCreateAt(LocalDateTime.now());
        mockUser.setUpdateAt(LocalDateTime.now());

        // MockUseDto to update the user -> (Mock userDto updated Coming from the body)
        UserDto userUpdatedDto = UserDto.builder()
                .username("Jhonattan")
                        .email("jhoalmanza@gmail.com")
                                .password("1221").build();


        //Mock user Updated -> (Mock userEntity updated)

        UserEntity userUpdated = UserEntity.builder()
                .username(userUpdatedDto.getUsername())
                        .email(userUpdatedDto.getEmail())
                                .password(userUpdatedDto.getPassword())
                                        .createAt(LocalDateTime.now())
                                                .updateAt(LocalDateTime.now())
                                                        .build();


        //When

        when(this.userRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(mockUser));
        when(this.userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userUpdated);

        Optional<UserEntity> userOptionalEntity =  this.userRepository.findById(1L);
        UserEntity userFound = userOptionalEntity.get();

        userFound.setUsername(userUpdatedDto.getUsername());
        userFound.setEmail(userUpdatedDto.getEmail());
        userFound.setPassword(userUpdatedDto.getPassword());
        userFound.setUpdateAt(LocalDateTime.now());

        UserEntity userSaved = this.userRepository.save(userFound);

        //Then

        assertThat(userOptionalEntity.isPresent()).isEqualTo(true);
        assertThat(userSaved.getUsername()).isEqualTo("Jhonattan");
        assertThat(userSaved.getEmail()).endsWith(".com");


    }
}