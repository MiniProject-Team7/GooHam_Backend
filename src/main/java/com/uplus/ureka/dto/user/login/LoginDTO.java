package com.uplus.ureka.dto.user.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String member_email; // id -> email
    private String member_password; // password
    private int delflag;

}