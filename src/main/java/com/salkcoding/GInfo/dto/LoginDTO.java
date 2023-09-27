package com.salkcoding.GInfo.dto;

import com.salkcoding.GInfo.data.Login;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class LoginDTO {
    private String id;
    private String pw;

    public Login toLogin(){
        return new Login(id,pw);
    }
}
