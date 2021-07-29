package com.ui2.securityLogin.service;

import com.ui2.securityLogin.domain.UserInfo;
import com.ui2.securityLogin.dto.UserInfoDto;
import com.ui2.securityLogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    /* 회원 정보 저장
    @Param infoDto 회원정보가 들어있는 DT
    @return 저장되는 회원의 PK
    */

    public Long save(UserInfoDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

          return userRepository.save(UserInfo.builder()
                .email(infoDto.getEmail())
                .auth(infoDto.getAuth())
                .NAME(infoDto.getName())
                .password(infoDto.getPassword()).build()).getCode();


       /* UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loadUserByUsername(infoDto.getEmail()).getEmail(),
                        null,loadUserByUsername(infoDto.getEmail()).getAuthorities());

        return authenticationToken;*/

    }


        /* Spring Security 필수 메소드 구현
         @param email 이메일
         @return UserDetails
         @throws UsernameNotFoundException 유저가 없을 때 예외 발생
         */

    @Override
    public UserInfo loadUserByUsername (String email)throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
    }
}
