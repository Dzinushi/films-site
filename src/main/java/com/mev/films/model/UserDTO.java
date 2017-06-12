package com.mev.films.model;

public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private short enabled;

    public UserDTO(){
    }

    public UserDTO(String login, String password, short enabled){
        setLogin(login);
        setPassword(password);
        setEnabled(enabled);
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

    public short getEnabled() {
        return enabled;
    }

    public void setEnabled(short enabled) {
        this.enabled = enabled;
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
