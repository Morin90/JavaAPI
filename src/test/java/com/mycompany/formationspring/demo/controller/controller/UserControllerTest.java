package com.mycompany.formationspring.demo.controller.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.formationspring.demo.controller.UserController;
import com.mycompany.formationspring.demo.dto.UserCreationDto;
import com.mycompany.formationspring.demo.entity.User;
import com.mycompany.formationspring.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UserCreationDto userCreationDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRoles(Collections.singleton("ROLE_USER"));

        userCreationDto = new UserCreationDto();
        userCreationDto.setUsername("testuser");
        userCreationDto.setPassword("password123");
        userCreationDto.setConfirmPassword("password123");
        userCreationDto.setRoles(Collections.singleton("ROLE_USER"));
    }

    @Test
    void testCreateUser() throws Exception {
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        logger.debug("Sending POST request to /api/user/create with payload: {}", objectMapper.writeValueAsString(userCreationDto));

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));

        logger.debug("Received response for POST request to /api/user/create");

        System.out.println("testCreateUser passed.");
    }

    @Test
    void testCreateUserWithInvalidPassword() throws Exception {
        userCreationDto.setPassword("short");
        userCreationDto.setConfirmPassword("short");

        logger.debug("Sending POST request to /api/user/create with invalid password payload: {}", objectMapper.writeValueAsString(userCreationDto));

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreationDto)))
                .andExpect(status().isBadRequest());

        logger.debug("Received response for POST request to /api/user/create with invalid password");

        System.out.println("testCreateUserWithInvalidPassword passed.");
    }

    @Test
    void testGetAllUsers() throws Exception {
        Mockito.when(userRepository.findAll(Mockito.any(Sort.class))).thenReturn(Collections.singletonList(user));

        logger.debug("Sending GET request to /api/user/");

        mockMvc.perform(get("/api/user/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"));

        logger.debug("Received response for GET request to /api/user/");

        System.out.println("testGetAllUsers passed.");
    }

    @Test
    void testGetUserById() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        logger.debug("Sending GET request to /api/user/1");

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        logger.debug("Received response for GET request to /api/user/1");

        System.out.println("testGetUserById passed.");
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        logger.debug("Sending GET request to /api/user/1 for non-existent user");

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isNotFound());

        logger.debug("Received response for GET request to /api/user/1 for non-existent user");

        System.out.println("testGetUserByIdNotFound passed.");
    }

    @Test
    void testUpdateUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("updatedUser");
        updatedUser.setPassword("updatedPassword123");
        updatedUser.setRoles(Collections.singleton("ROLE_USER"));

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        logger.debug("Sending PUT request to /api/user/update/1 with payload: {}", objectMapper.writeValueAsString(updatedUser));

        mockMvc.perform(put("/api/user/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"));

        logger.debug("Received response for PUT request to /api/user/update/1");

        System.out.println("testUpdateUser passed.");
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        logger.debug("Sending PUT request to /api/user/update/1 for non-existent user");

        mockMvc.perform(put("/api/user/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());

        logger.debug("Received response for PUT request to /api/user/update/1 for non-existent user");

        System.out.println("testUpdateUserNotFound passed.");
    }

    @Test
    void testDeleteUser() throws Exception {
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteById(1L);

        logger.debug("Sending DELETE request to /api/user/delete/1");

        mockMvc.perform(delete("/api/user/delete/1"))
                .andExpect(status().isNoContent());

        logger.debug("Received response for DELETE request to /api/user/delete/1");

        System.out.println("testDeleteUser passed.");
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        Mockito.when(userRepository.existsById(1L)).thenReturn(false);

        logger.debug("Sending DELETE request to /api/user/delete/1 for non-existent user");

        mockMvc.perform(delete("/api/user/delete/1"))
                .andExpect(status().isNotFound());

        logger.debug("Received response for DELETE request to /api/user/delete/1 for non-existent user");

        System.out.println("testDeleteUserNotFound passed.");
    }
}