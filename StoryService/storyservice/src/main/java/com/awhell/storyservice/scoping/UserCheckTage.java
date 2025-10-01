package com.awhell.storyservice.scoping;

import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Component
@ConditionalOnProperty(name = "check.transactional.tags", havingValue = "true", matchIfMissing = false)
public class UserCheckTage {

    public UserCheckTage() {
        System.out.println("User Check Tag Bean Initialized");
    }

    public String CheckUserTag(String tag) {
        if (tag == "UserCheckTag") {
            return "User Check Tag Matched";
        } else {
            return "User Check Tag Not Matched";
        }
    }

}
