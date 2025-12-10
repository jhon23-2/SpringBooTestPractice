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

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userService.create(userDto));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(){
        return ResponseEntity.ok()
                .body(this.userService.findAll());
    }

    @GetMapping("/find")
    public ResponseEntity<UserDto> findByUsername(@RequestParam String username){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.findUserByUsername(username).get());
    }

}
