package com.mev.films.model;

public class DiscountDTO {
    private Long id;
    private String code;

    public DiscountDTO(){
    }

    public DiscountDTO(String code){
        setCode(code);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("DiscountDTO [id = ");
        sb.append(id);
        sb.append(", discountCode = ");
        sb.append(code);
        sb.append("]");
        sb.append(super.toString());

        return sb.toString();
    }
}
