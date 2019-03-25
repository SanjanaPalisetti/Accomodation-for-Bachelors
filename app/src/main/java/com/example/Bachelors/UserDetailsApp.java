package com.example.Bachelors;

public class UserDetailsApp {
    String username , type;

    public UserDetailsApp(String username, String type) {
        this.username = username;
        this.type = type;
    }

    public UserDetailsApp() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
