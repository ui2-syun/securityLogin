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

                //요청을 전송하는 역할
                //http메소드 결정(get,post,put,delete)
        mockMvc.perform(get("/"))
                //응답을 검증
                //상태코드 ( status() )
                // isOk():200  , isNotFound():404 ,
                // isMethodNotAllowed() : 405 , isInternalServerError() : 500
                .andExpect(status().isOk())
                //요청, 응답 전체 메세지 확인
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

    @DisplayName("어드민 유저 진입")
    //성공
    @Test
    @WithMockUser(username = "test", roles = "USER")
    public void test_logout() throws Exception{

        mockMvc.perform(post("/logout"))
                .andExpect(redirectedUrl("/logout"))
                .andDo(print());
    }




    /*@Test
    @Transactional
    public void login_success() throws Exception {
        String username = "test";
        String password = "123";


    }*/



}