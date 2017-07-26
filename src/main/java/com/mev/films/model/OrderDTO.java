package com.mev.films.model;

import java.sql.Timestamp;

public class OrderDTO {

    private Long id;
    private UserDTO userDTO;
    private FilmDTO filmDTO;
    private DiscountDTO discountDTO;
    private Boolean mark;
    private Timestamp time;

    public OrderDTO(){
    }

    public OrderDTO(UserDTO userDTO, FilmDTO filmDTO, DiscountDTO discountDTO, Boolean mark){
        this.userDTO = userDTO;
        this.filmDTO = filmDTO;
        this.discountDTO = discountDTO;
        this.mark = mark;
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

    public FilmDTO getFilmDTO() {
        return filmDTO;
    }

    public void setFilmDTO(FilmDTO filmDTO) {
        this.filmDTO = filmDTO;
    }

    public DiscountDTO getDiscountDTO() {
        return discountDTO;
    }

    public void setDiscountDTO(DiscountDTO discountDTO) {
        this.discountDTO = discountDTO;
    }

    public Boolean isMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        if (isMark() != orderDTO.isMark()) return false;
        if (getUserDTO() != null ? !getUserDTO().equals(orderDTO.getUserDTO()) : orderDTO.getUserDTO() != null)
            return false;
        if (getFilmDTO() != null ? !getFilmDTO().equals(orderDTO.getFilmDTO()) : orderDTO.getFilmDTO() != null)
            return false;
        return getDiscountDTO() != null ? getDiscountDTO().equals(orderDTO.getDiscountDTO()) : orderDTO.getDiscountDTO() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserDTO() != null ? getUserDTO().hashCode() : 0;
        result = 31 * result + (getFilmDTO() != null ? getFilmDTO().hashCode() : 0);
        result = 31 * result + (getDiscountDTO() != null ? getDiscountDTO().hashCode() : 0);
        result = 31 * result + (isMark() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", filmDTO=" + filmDTO +
                ", discountDTO=" + discountDTO +
                ", mark=" + mark +
                ", time=" + time +
                '}';
    }
}
