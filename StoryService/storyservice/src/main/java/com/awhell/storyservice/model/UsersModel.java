package com.awhell.storyservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersModel {
    private Integer userId;
    private String userName;
    private String email;
    private String userPassword;
    private String confirmPassword;
}
