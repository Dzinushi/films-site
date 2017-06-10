package com.mev.films.model;

public class UserRoleDTO {
    private Long id;
    private String login;
    private short enabled;

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

    public short getEnabled() {
        return enabled;
    }

    public void setEnabled(short enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("UserRoleDTO [id = ");
        sb.append(id);
        sb.append(", login = ");
        sb.append(login);
        sb.append(", enabled = ");
        sb.append(enabled);
        sb.append(super.toString());

        return sb.toString();
    }
}
