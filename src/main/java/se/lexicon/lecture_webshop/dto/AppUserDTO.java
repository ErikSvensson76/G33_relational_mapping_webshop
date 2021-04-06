package se.lexicon.lecture_webshop.dto;

import se.lexicon.lecture_webshop.entity.AppUser;

import java.io.Serializable;

public class AppUserDTO implements Serializable {

    private String userId;
    private String username;


    public AppUserDTO(AppUser appUser){
        if(appUser != null){
            userId = appUser.getUserId();
            username = appUser.getUsername();
        }
    }

    public AppUserDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
