package com.mev.films.model;


import java.sql.Timestamp;

public class PaymentDTO {
    private Long id;
    private BasketDTO basketDTO;
    private int count;
    private Timestamp time;


    public PaymentDTO(){

    }

    public PaymentDTO(BasketDTO basketDTO, int count){
        this.basketDTO = basketDTO;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BasketDTO getBasketDTO() {
        return basketDTO;
    }

    public void setBasketDTO(BasketDTO basketDTO) {
        this.basketDTO = basketDTO;
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

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "id=" + id +
                ", basketDTO=" + basketDTO +
                ", count=" + count +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentDTO)) return false;

        PaymentDTO that = (PaymentDTO) o;

        if (getCount() != that.getCount()) return false;
        return getBasketDTO() != null ? getBasketDTO().equals(that.getBasketDTO()) : that.getBasketDTO() == null;
    }

    @Override
    public int hashCode() {
        int result = getBasketDTO() != null ? getBasketDTO().hashCode() : 0;
        result = 31 * result + getCount();
        return result;
    }
}
