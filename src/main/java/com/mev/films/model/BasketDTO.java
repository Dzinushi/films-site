package com.mev.films.model;

import java.sql.Timestamp;

public class BasketDTO {
    private Long id;
    private UserDTO userDTO;
    private Timestamp time;

    public BasketDTO(){

    }

    public BasketDTO(UserDTO userDTO){
        this.userDTO = userDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Timestamp getTime() {
        return time;
    }
}
