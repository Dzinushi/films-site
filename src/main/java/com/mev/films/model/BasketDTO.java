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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketDTO)) return false;

        BasketDTO basketDTO = (BasketDTO) o;

        return getUserDTO() != null ? getUserDTO().equals(basketDTO.getUserDTO()) : basketDTO.getUserDTO() == null;
    }

    @Override
    public int hashCode() {
        return getUserDTO() != null ? getUserDTO().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BasketDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", time=" + time +
                '}';
    }
}
