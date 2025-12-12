package com.api.Test.controller;

import com.api.Test.dto.UserDto;
import com.api.Test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    //Tested
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userService.create(userDto));
    }
    // Tested
    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUser(){
        return ResponseEntity.ok()
                .body(this.userService.findAll());
    }

    // Tested
    @GetMapping("/find")
    public ResponseEntity<UserDto> findByUsername(@RequestParam String username){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.findUserByUsername(username).get());
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam(name = "id") Long id, @RequestBody UserDto userDto){

        if(id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You must provide id");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userService.updateUserDto(id,userDto));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam(name = "id") Long id){

        if(id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You must provide id");
        }

        this.userService.deleteUserById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
