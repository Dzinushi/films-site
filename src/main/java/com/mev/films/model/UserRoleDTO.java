package com.mev.films.model;

public class UserRoleDTO {
    private Long id;
    private String login;
    private String role;

    public UserRoleDTO(){

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

    public String getRole() {
        return role;
    }

    public void setEnabled(String role) {
        this.role = role;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("UserRoleDTO [id = ");
        sb.append(id);
        sb.append(", login = ");
        sb.append(login);
        sb.append(", enabled = ");
        sb.append(role);
        sb.append(super.toString());

        return sb.toString();
    }
}
