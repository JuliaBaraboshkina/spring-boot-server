package com.parazitik.graduatework.Model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDTO {
    private Long id;
    private String firstName;
    private String secondName;
    private String password;
    private String email;
    private String avatarUrl;
}
