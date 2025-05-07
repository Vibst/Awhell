package com.socilanetworking.postservice.model;

import java.security.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserSendPostModel {

    private String userId;
    private String username;
    private String userCreatedBy;
    private String userAffulatedBy;
    private Timestamp createUser;

}
