package com.api.Test.controller;

import com.api.Test.dto.UserDto;
import com.api.Test.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService; // Mock userService only matters controller layers


    @Test
    void findUserByUsernameControllerTest() throws Exception {
        // Given -> Mock User Found

        UserDto mockUser = UserDto.builder()
                .username("Sultan")
                .email("sultan@gmail.com")
                .password("1212")
                .createAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();

        //When
        Mockito.when(this.userService.findUserByUsername(Mockito.any(String.class)))
                .thenReturn(Optional.ofNullable(mockUser));


        // Then

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/find?username=Sultan"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Sultan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("sultan@gmail.com"));

    }

    @Test
    void findAllUserControllerTest() throws Exception {

        //Given

        List<UserDto> userDtos = Arrays.asList(
                UserDto.builder().username("Jhonattan").email("jh@gmail.com").password("""
                        1212""").build(),
                UserDto.builder().username("Sultan").email("sultan@gmail.com").password("3232").build()
        );

        // when
        Mockito.when(this.userService.findAll()).thenReturn(userDtos);

        // Then

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("Jhonattan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("Sultan"));

    }

    @Test
    void createUserControllerTest() throws Exception {
        // Given

        // User request
        UserDto userRequestDto = UserDto.builder()
                .username("Jhonattan")
                .email("jho@gmail.com")
                .password("1212")
                .build();

        // User Response

        UserDto userResponse = UserDto.builder()
                .username("Jhonattan")
                .email("jho@gmail.com")
                .password("1212")
                .createAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();

        // When

        Mockito.when(this.userService.create(userRequestDto))
                .thenReturn(userResponse);

        // then

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Jhonattan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("jho@gmail.com"));

    }

    @Test
    void updateUserControllerTest() throws Exception {
        // Given

        // request dto
        Long id = 6L;
        UserDto userRequestDto = UserDto.builder()
                .username("Saltarin")
                .email("saltarin@gmail.com")
                .password("1221").build();

        // response dto

        UserDto userResponseDto = UserDto.builder()
                .username("Saltarin")
                .email("saltarin@gmail.com")
                .password("1221")
                .createAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();

        // When

        Mockito.when(this.userService.updateUserDto(id, userRequestDto))
                .thenReturn(userResponseDto);

        // Then

        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/update?id=6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(userRequestDto))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Saltarin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("saltarin@gmail.com"));



    }

    @Test
    void deleteUserControllerTest_whenIdProvided_callsServiceAndReturnsNoContent() throws Exception {
        // Given
        Long id = 6L;
        Mockito.doNothing().when(this.userService).deleteUserById(id);

        // When / then
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api?id=6"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(this.userService).deleteUserById(id);
    }


}