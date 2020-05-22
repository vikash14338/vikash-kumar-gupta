package com.example.autochat;

public class Message {
    String user;
    String message;
    private String imageUrl;
    public Message(){}

    public Message(String user, String message, String imageUrl) {
        this.user = user;
        this.message = message;
        this.imageUrl = imageUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
