package com.mev.films.model;

import java.math.BigInteger;

public class DiscountDTO {
    private BigInteger discountId;
    private String dicountCode;

    public DiscountDTO(){

    }

    public BigInteger getDiscountId() {
        return discountId;
    }

    public void setDiscountId(BigInteger discountId) {
        this.discountId = discountId;
    }

    public String getDicountCode() {
        return dicountCode;
    }

    public void setDicountCode(String dicountCode) {
        this.dicountCode = dicountCode;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("DiscountDTO [discountId = ");
        sb.append(discountId);
        sb.append(", discountCode = ");
        sb.append(dicountCode);
        sb.append(super.toString());

        return sb.toString();
    }
}
