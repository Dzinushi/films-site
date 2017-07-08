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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountDTO)) return false;

        DiscountDTO that = (DiscountDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getCode() != null ? getCode().equals(that.getCode()) : that.getCode() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        return result;
    }
}
