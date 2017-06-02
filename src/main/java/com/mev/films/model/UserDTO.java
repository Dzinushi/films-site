package com.mev.films.model;

import java.math.BigInteger;

public class UserDTO {
    private BigInteger userId;
    private String userLogin;
    private String userPassword;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString(){
        return "UserDTO [userId = ]" + userId + "]" + super.toString();
    }
}
