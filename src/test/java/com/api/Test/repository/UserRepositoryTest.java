package com.api.Test.repository;

import com.api.Test.entity.UserEntity;
import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest // By defaults spring boot this annotation handles the database configuration so the next annotation is optional but is good sometime keep it in mind
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp(){
        this.userRepository.deleteAll();
    }

    @Test
    void findUserByUsernameTest(){

        // given
        UserEntity user = UserEntity.builder()
                .username("Sultan")
                .email("Sultan@gmail.com")
                .password("sultan121")
                .build();

        this.userRepository.saveAndFlush(user);

        // when

        Optional<UserEntity> userEntityOptional = this.userRepository.findByUsername("Sultan");

        //then

        assertTrue(userEntityOptional.isPresent());
        assertEquals("Sultan", userEntityOptional.get().getUsername());
        assertEquals("Sultan@gmail.com", userEntityOptional.get().getEmail());
    }


    @Test
    void userByUsernameNotFoundTest(){
        // when
        Optional<UserEntity> userEntityOptional  = this.userRepository.findByUsername("Sultan");

        //then
        assertTrue(userEntityOptional.isEmpty());
    }

    @Test
    void saveAllUsersTest() {

        // given
        var userOne = UserEntity.builder().username("Jhonnattan").email("Jhona@gmail.com").password("sasa").build();
        var userTwo = UserEntity.builder().username("Sandy").email("sandy@gmail.com").password("sadsw").build();

        this.userRepository.save(userOne);
        this.userRepository.save(userTwo);

        // when

        List<UserEntity> userEntities = this.userRepository.findAll();

        //then
        assertThat(userEntities).isNotNull();
        assertThat(userEntities.size()).isEqualTo(2);
    }

    @Test
    void updateUserTest(){
        // given
        UserEntity user = UserEntity.builder()
                .username("Jhonnatan")
                .email("jhona@gmail.com")
                .password("121")
                .build();

        UserEntity userSaved =  this.userRepository.save(user);

        // when

        UserEntity userEntityFound = this.userRepository.findById(userSaved.getId()).get();
        userEntityFound.setUsername("Leiner");
        userEntityFound.setEmail("leiner@gmail.com");

        UserEntity userUpdated = this.userRepository.save(userEntityFound);

        // then

        assertThat(userUpdated).isNotNull();
        assertEquals("Leiner", userUpdated.getUsername());
        assertEquals("leiner@gmail.com", userUpdated.getEmail());

    }
}