package com.mev.films.model;

public class UserDTO {
    private Long id;
    private String login;
    private String password;

    public UserDTO(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("UserDTO [id = ");
        sb.append(id);
        sb.append(", login = ");
        sb.append(login);
        sb.append("]");
        sb.append(super.toString());

        return sb.toString();
    }
}
