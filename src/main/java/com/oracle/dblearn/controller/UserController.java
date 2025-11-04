package com.oracle.dblearn.controller;

import com.oracle.dblearn.infra.utils.APIMessageReturn;
import com.oracle.dblearn.controller.dto.UserCreateDTO;
import com.oracle.dblearn.model.entity.User;
import com.oracle.dblearn.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<APIMessageReturn> createUser(@Valid @RequestBody UserCreateDTO dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());

        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIMessageReturn("User registered successfully"));
    }

    @GetMapping
    public String healthCheck() {
        return "User Service is running.";
    }

}
