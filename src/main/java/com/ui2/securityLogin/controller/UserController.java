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
    public String signup(UserInfoDto infoDto)  { // 회원 추가



        //회원 가입 이후의 정보 등록과 자동 로그인 처리

        //service에서 리턴 한 authenticationToken을
        Authentication auth =  userService.save(infoDto);
        //Spring Security에서 담은(service에서 리턴) AuthenticationToken을 SecurityContext에 보관
        //SecurityContext를 SecurityContextHolder에 보관
        // Authentication 인스턴스를 밑의 코드에 set
        SecurityContextHolder.getContext().setAuthentication(auth); //로그인 처리

        return  "redirect:/";
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


    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
