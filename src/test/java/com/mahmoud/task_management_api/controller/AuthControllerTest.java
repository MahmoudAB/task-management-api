package com.mahmoud.task_management_api.controller;

import com.mahmoud.task_management_api.entity.User;
import com.mahmoud.task_management_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    void registerEncodesPasswordAssignsUserRoleAndSavesUser() throws Exception {
        when(passwordEncoder.encode("plain-password")).thenReturn("encoded-password");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "mahmoud",
                                  "password": "plain-password",
                                  "role": "ADMIN"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered"));

        verify(passwordEncoder).encode("plain-password");
        verify(userRepository).save(argThat(user ->
                "mahmoud".equals(user.getUsername())
                        && "encoded-password".equals(user.getPassword())
                        && "USER".equals(user.getRole())));
    }
}
