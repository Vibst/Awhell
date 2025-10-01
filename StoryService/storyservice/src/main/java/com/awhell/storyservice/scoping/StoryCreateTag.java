package com.awhell.storyservice.scoping;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "check.transactional.tags", havingValue = "false", matchIfMissing = false)
public class StoryCreateTag {

    public StoryCreateTag() {
        System.out.println("Story Create Tag Bean Initialized");
    }

    public String CheckStoryTag(String tag) {
        if (tag == "StoryCreateTag") {
            return "Story Create Tag Matched";
        } else {
            return "Story Create Tag Not Matched";
        }
    }

}