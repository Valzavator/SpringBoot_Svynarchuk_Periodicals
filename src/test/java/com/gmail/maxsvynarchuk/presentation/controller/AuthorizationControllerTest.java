package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.presentation.util.constants.PagesPaths;
import com.gmail.maxsvynarchuk.presentation.util.constants.Views;
import com.gmail.maxsvynarchuk.service.UserService;
import com.gmail.maxsvynarchuk.util.type.RoleType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthorizationController.class)
class AuthorizationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final String email = "email@gmail.com";
    private final String password = "password";

    @Test
    void getSignInPageTest() throws Exception {
        mockMvc.perform(get("/app/signin"))
                .andExpect(status().isOk())
                .andExpect(view().name(Views.SIGN_IN_VIEW))
                .andExpect(forwardedUrl(Views.PREFIX + Views.SIGN_IN_VIEW + Views.SUFFIX));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void signOutTest() throws Exception {
        mockMvc.perform(get("/app/signout"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(PagesPaths.HOME_PATH));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void signInAsUserTest() throws Exception {
        User expected = User.builder()
                .email(email)
                .password(password)
                .role(RoleType.USER.getValue())
                .build();
        when(userService.signIn(email, password)).thenReturn(Optional.of(expected));

        mockMvc.perform(post("/app/signin")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", email)
                .param("password", password)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(PagesPaths.CATALOG_PATH))
                .andExpect(request().sessionAttribute("user", hasProperty("email", is(email))))
                .andExpect(request().sessionAttribute("user", hasProperty("password", is(password))));

        ArgumentCaptor<String> emailArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordArgument = ArgumentCaptor.forClass(String.class);
        verify(userService, times(1)).signIn(emailArgument.capture(), passwordArgument.capture());
        verifyNoMoreInteractions(userService);

        assertEquals(email, emailArgument.getValue());
        assertEquals(password, passwordArgument.getValue());
    }

    @Test
    void signInAsAdminTest() throws Exception {
        User expected = User.builder()
                .email(email)
                .password(password)
                .role(RoleType.ADMIN.getValue())
                .build();
        when(userService.signIn(email, password)).thenReturn(Optional.of(expected));

        mockMvc.perform(post("/app/signin")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", email)
                .param("password", password)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(PagesPaths.ADMIN_CATALOG_PATH))
                .andExpect(request().sessionAttribute("user", hasProperty("email", is(email))))
                .andExpect(request().sessionAttribute("user", hasProperty("password", is(password))));

        ArgumentCaptor<String> emailArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordArgument = ArgumentCaptor.forClass(String.class);
        verify(userService, times(1)).signIn(emailArgument.capture(), passwordArgument.capture());
        verifyNoMoreInteractions(userService);

        assertEquals(email, emailArgument.getValue());
        assertEquals(password, passwordArgument.getValue());
    }

    @Test
    void signInWithInvalidCredentialsTest() throws Exception {
        when(userService.signIn(email, password)).thenReturn(Optional.empty());

        mockMvc.perform(post("/app/signin")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", email)
                .param("password", password)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(PagesPaths.SIGN_IN_PATH))
                .andExpect(flash().attribute("errorAuthentication", is(true)))
                .andExpect(flash().attribute("signInDTO", hasProperty("email", is(email))))
                .andExpect(flash().attribute("signInDTO", hasProperty("password", is(password))));

        ArgumentCaptor<String> emailArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordArgument = ArgumentCaptor.forClass(String.class);
        verify(userService, times(1)).signIn(emailArgument.capture(), passwordArgument.capture());
        verifyNoMoreInteractions(userService);

        assertEquals(email, emailArgument.getValue());
        assertEquals(password, passwordArgument.getValue());
    }

    @Test
    void signInWithInvalidEmailTest() throws Exception {
        String invalidEmail = "invalid@email";
        mockMvc.perform(post("/app/signin")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", invalidEmail)
                .param("password", password)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(PagesPaths.SIGN_IN_PATH))
                .andExpect(flash().attribute("errors", hasEntry("emailError", true)))
                .andExpect(flash().attribute("signInDTO", hasProperty("email", is(invalidEmail))))
                .andExpect(flash().attribute("signInDTO", hasProperty("password", is(password))));

        verify(userService, never()).signIn(anyString(), anyString());
    }

    @Test
    void signInWithInvalidPasswordTest() throws Exception {
        String invalidPassword = "0";
        mockMvc.perform(post("/app/signin")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", email)
                .param("password", invalidPassword)
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(PagesPaths.SIGN_IN_PATH))
                .andExpect(flash().attribute("errors", hasEntry("passwordError", true)))
                .andExpect(flash().attribute("signInDTO", hasProperty("email", is(email))))
                .andExpect(flash().attribute("signInDTO", hasProperty("password", is(invalidPassword))));

        verify(userService, never()).signIn(anyString(), anyString());
    }
}