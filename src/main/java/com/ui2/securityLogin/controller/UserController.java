package com.ui2.securityLogin.controller;

import com.ui2.securityLogin.config.WebSecurityConfig;
import com.ui2.securityLogin.domain.UserInfo;
import com.ui2.securityLogin.dto.UserInfoDto;
import com.ui2.securityLogin.repository.UserRepository;
import com.ui2.securityLogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.h2.engine.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.SecurityContextLoginModule;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/user")
    public String signup(UserInfoDto infoDto,HttpServletRequest request, HttpServletResponse response) throws ServletException { // 회원 추가


        Authentication auth =  userService.save(infoDto);
        SecurityContextHolder.getContext().setAuthentication(auth);

        //
        /*try {
            userService.loadUserByUsername(infoDto.getEmail());
        }catch (UsernameNotFoundException e){
            userRepository.findByEmail(infoDto.getEmail()).orElseThrow(()-> new UsernameNotFoundException(infoDto.getEmail()));

        }*/

        //세션 확인
        /*HttpSession session = (HttpSession)request.getSession();
        String name = (String) session.getAttribute("sessionId");
        System.out.println("===================================");
        System.out.println("inserted name in session:" + name);
        System.out.println("===================================");

        name = "hello";
        session.setAttribute("sessionId",name);*/

           return  "redirect:/";



    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
