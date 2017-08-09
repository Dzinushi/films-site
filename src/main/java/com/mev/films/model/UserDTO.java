package com.mev.films.model;

import java.sql.Timestamp;
import java.util.Objects;

public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private Short enabled;
    private Timestamp time;

    public UserDTO(){
    }

    public UserDTO(String login, String password, Short enabled){
        this.login = login;
        this.password = password;
        this.enabled = enabled;
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

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(short enabled) {
        this.enabled = enabled;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        UserDTO userDTO = (UserDTO) o;

        if (getLogin() != null ? !getLogin().equals(userDTO.getLogin()) : userDTO.getLogin() != null) return false;
        if (getPassword() != null ? !getPassword().equals(userDTO.getPassword()) : userDTO.getPassword() != null)
            return false;
        return getEnabled() != null ? getEnabled().equals(userDTO.getEnabled()) : userDTO.getEnabled() == null;
    }

    @Override
    public int hashCode() {
        int result = getLogin() != null ? getLogin().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getEnabled() != null ? getEnabled().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", time=" + time +
                '}';
    }
}
