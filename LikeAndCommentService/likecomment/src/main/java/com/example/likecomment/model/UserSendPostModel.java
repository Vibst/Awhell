package com.example.likecomment.model;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserSendPostModel {

    private String userId;
    private String username;
    private String userCreatedBy;
    private String userAffulatedBy;
    private LocalDateTime createUser;

}
