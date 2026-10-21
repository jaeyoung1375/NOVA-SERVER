package kr.co.nova.auth.dto;

import lombok.Getter;

@Getter
public class GoogleUserResponse {

    private String sub;

    private String email;

    private String name;
}
