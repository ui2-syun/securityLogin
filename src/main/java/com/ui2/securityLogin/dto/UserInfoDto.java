package com.ui2.securityLogin.dto;

import lombok.*;


@Getter
@Setter
public class UserInfoDto {
    private String email;
    private String password;
    private String name;

    private String auth;
}
