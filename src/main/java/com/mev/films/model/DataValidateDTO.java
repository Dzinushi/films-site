package com.mev.films.model;

import java.sql.Timestamp;

public class DataValidateDTO {
    private Long id;
    private String field;
    private String store;
    Timestamp time;

    public DataValidateDTO(){
    }

    public DataValidateDTO(String field, String store){
        this.field = field;
        this.store = store;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataValidateDTO)) return false;

        DataValidateDTO that = (DataValidateDTO) o;

        if (getField() != null ? !getField().equals(that.getField()) : that.getField() != null) return false;
        return getStore() != null ? getStore().equals(that.getStore()) : that.getStore() == null;
    }

    @Override
    public int hashCode() {
        int result = getField() != null ? getField().hashCode() : 0;
        result = 31 * result + (getStore() != null ? getStore().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataValidateDTO{" +
                "id=" + id +
                ", field='" + field + '\'' +
                ", store='" + store + '\'' +
                ", time=" + time +
                '}';
    }
}

