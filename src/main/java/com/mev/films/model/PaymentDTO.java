package com.mev.films.model;


import java.sql.Timestamp;

public class PaymentDTO {
    private Long id;
    private UserDTO userDTO;
    private FilmDTO filmDTO;
    private DiscountDTO discountDTO;
    private Integer totalPrice;
    private Timestamp time;

    public PaymentDTO(){
    }

    public PaymentDTO(UserDTO userDTO, FilmDTO filmDTO, DiscountDTO discountDTO, Integer totalPrice){
        this.userDTO = userDTO;
        this.filmDTO = filmDTO;
        this.discountDTO = discountDTO;
        this.totalPrice = totalPrice;
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

    public void setTotalPrice(Integer totalPrice){
        this.totalPrice = totalPrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentDTO)) return false;

        PaymentDTO that = (PaymentDTO) o;

        if (getUserDTO() != null ? !getUserDTO().equals(that.getUserDTO()) : that.getUserDTO() != null) return false;
        if (getFilmDTO() != null ? !getFilmDTO().equals(that.getFilmDTO()) : that.getFilmDTO() != null) return false;
        if (getDiscountDTO() != null ? !getDiscountDTO().equals(that.getDiscountDTO()) : that.getDiscountDTO() != null)
            return false;
        return getTotalPrice() != null ? getTotalPrice().equals(that.getTotalPrice()) : that.getTotalPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserDTO() != null ? getUserDTO().hashCode() : 0;
        result = 31 * result + (getFilmDTO() != null ? getFilmDTO().hashCode() : 0);
        result = 31 * result + (getDiscountDTO() != null ? getDiscountDTO().hashCode() : 0);
        result = 31 * result + (getTotalPrice() != null ? getTotalPrice().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", filmDTO=" + filmDTO +
                ", discountDTO=" + discountDTO +
                ", totalPrice=" + totalPrice +
                ", time=" + time +
                '}';
    }
}
