package com.ui2.securityLogin.config;

import com.ui2.securityLogin.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityConfigTest extends WebSecurityConfigurerAdapter {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @DisplayName("익명 유저 진입")
    @Test
    @WithAnonymousUser
    public void test_annonymous() throws Exception{

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("일반 유저 진입")
    //성공
    @Test
    @WithMockUser(username = "test", roles = "USER")
    public void test_user() throws Exception{

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /*@DisplayName("일반 유저 어드민 진입")
    //실패
    @Test
    @WithMockUser(username = "test", roles = "USER")
    public void test_user() throws Exception{

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andDo(print());
    }*/

    @DisplayName("어드민 유저 진입")
    //성공
    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    public void test_admin() throws Exception{

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andDo(print());
    }




    /*@Test
    @Transactional
    public void login_success() throws Exception {
        String username = "test";
        String password = "123";


    }*/



}