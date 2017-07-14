package com.mev.films.model;


import java.sql.Timestamp;

public class PaymentDTO {
    private Long id;
    private UserDTO userDTO;
    private FilmDTO filmDTO;
    private DiscountDTO discountDTO;
    private int count;
    private Timestamp time;


    public PaymentDTO(){

    }

    public PaymentDTO(UserDTO userDTO, FilmDTO filmDTO, DiscountDTO discountDTO, int count, Timestamp time){
        this.userDTO = userDTO;
        this.filmDTO = filmDTO;
        this.discountDTO = discountDTO;
        this.count = count;
        this.time = time;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentDTO)) return false;

        PaymentDTO that = (PaymentDTO) o;

        if (getCount() != that.getCount()) return false;
        if (getUserDTO() != null ? !getUserDTO().equals(that.getUserDTO()) : that.getUserDTO() != null) return false;
        if (getFilmDTO() != null ? !getFilmDTO().equals(that.getFilmDTO()) : that.getFilmDTO() != null) return false;
        return getDiscountDTO() != null ? getDiscountDTO().equals(that.getDiscountDTO()) : that.getDiscountDTO() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUserDTO() != null ? getUserDTO().hashCode() : 0);
        result = 31 * result + (getFilmDTO() != null ? getFilmDTO().hashCode() : 0);
        result = 31 * result + (getDiscountDTO() != null ? getDiscountDTO().hashCode() : 0);
        result = 31 * result + getCount();
        return result;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", filmDTO=" + filmDTO +
                ", discountDTO=" + discountDTO +
                ", count=" + count +
                ", time='" + time + '\'' +
                '}';
    }
}
