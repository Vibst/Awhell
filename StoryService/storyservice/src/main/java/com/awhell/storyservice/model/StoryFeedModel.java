package com.awhell.storyservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StoryFeedModel {

    private Long id;
    private String content;
    private String author;
    private Integer userId;

    public UsersModel usersModel;

}
