package org.example.calendar.model;

import lombok.Getter;

@Getter
public enum Theme {
    ART("art"),
    NATURE("nature"),
    FRENCH_IMPRESSIONISTS("french-impressionists"),
    CALIFORNIA("california"),
    LONDON("london"),
    ITALY("italy");

    private String path;
    Theme(String path) {
        this.path = path;
    }
}
