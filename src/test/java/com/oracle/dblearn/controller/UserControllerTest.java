package com.oracle.dblearn.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.dblearn.controller.dto.UserCreateDTO;
import com.oracle.dblearn.exception.EmailAlreadyExistsException;
import com.oracle.dblearn.exception.InvalidPasswordException;
import com.oracle.dblearn.model.entity.User;
import com.oracle.dblearn.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn201WhenUserIsCreatedSuccessfully() throws Exception {
        UserCreateDTO dto = new UserCreateDTO("Calebe", "SenhaValida123@#", "calebe@email.com");

        when(userService.createUser(any(User.class))).thenReturn(new User());

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    void shouldReturn409WhenEmailAlreadyExists() throws Exception {
        UserCreateDTO dto = new UserCreateDTO("Calebe", "SenhaValida123@#", "calebe@email.com");

        when(userService.createUser(any(User.class)))
                .thenThrow(new EmailAlreadyExistsException("E-mail already exists"));

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("E-mail already exists"));
    }

    @Test
    void shouldReturn400WhenPasswordIsInvalid() throws Exception {
        UserCreateDTO dto = new UserCreateDTO("Calebe", "123", "calebe@email.com");

        when(userService.createUser(any(User.class)))
                .thenThrow(new InvalidPasswordException("Invalid Password"));

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid Password"));
    }


}

