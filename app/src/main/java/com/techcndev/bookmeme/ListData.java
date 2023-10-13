package com.techcndev.bookmeme;

public class ListData {
    private String username, stage_level;

    public ListData(String titleText, String description) {
        this.username = titleText;
        this.stage_level = description;
    }
    public String getContent() {
        return stage_level;
    }
    public void setDescription(String description) {
        this.stage_level = description;
    }
    public String getTitle() {
        return username;
    }
    public void setTitle(String titleText) {
        this.username = titleText;
    }
}
