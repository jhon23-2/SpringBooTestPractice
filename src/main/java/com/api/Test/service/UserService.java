package com.api.Test.service;

import com.api.Test.dto.UserDto;
import com.api.Test.entity.UserEntity;
import com.api.Test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto create(UserDto userDto){
        UserEntity user = this.userRepository.save(UserEntity.builder()
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .password(userDto.getPassword())
                        .createAt(LocalDateTime.now())
                        .updateAt(LocalDateTime.now())
                .build());

        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .createAt(user.getCreateAt().toString())
                .updatedAt(user.getUpdateAt().toString())
                .build();
    }

    public List<UserDto> findAll(){
        return this.userRepository.findAll()
                .stream().map(userEntity -> UserDto.builder()
                        .username(userEntity.getUsername())
                        .email(userEntity.getEmail())
                        .password(userEntity.getPassword())
                        .createAt(userEntity.getCreateAt().toString())
                        .updatedAt(userEntity.getUpdateAt().toString())
                        .build()).toList();
    }

    public Optional<UserDto> findUserByUsername(String username){
        Optional<UserEntity> userEntityOptional = this.userRepository.findByUsername(username);

        return userEntityOptional.map(userEntity -> UserDto.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .createAt(userEntity.getCreateAt().toString())
                .updatedAt(userEntity.getUpdateAt().toString())
                .build()).or(() -> Optional.of(new UserDto()));

    }

    public UserDto updateUserDto(Long id, UserDto userDto) {

        // find the user by id
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);

        if(userEntityOptional.isEmpty()) {
            return new UserDto();
        }

        // get the user and update fields

        UserEntity userFound = userEntityOptional.get();
        userFound.setUsername(userDto.getUsername());
        userFound.setEmail(userDto.getEmail());
        userFound.setPassword(userDto.getPassword());
        userFound.setUpdateAt(LocalDateTime.now());

        UserEntity userSaved = this.userRepository.save(userFound);


        // return userDto to the client -> DTO Pattern design
        return UserDto.builder()
                .username(userSaved.getUsername())
                .email(userSaved.getEmail())
                .password(userSaved.getEmail())
                .createAt(userSaved.getCreateAt().toString())
                .updatedAt(userSaved.getUpdateAt().toString())
                .build();
    }
}
