package com.prodapt.mobileplan.dto.response;

public class PlanResponse {

    private Long id;
    private String name;
    private String type;
    private double price;
    private int dataInGb;
    private int validityInDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDataInGb() {
        return dataInGb;
    }

    public void setDataInGb(int dataInGb) {
        this.dataInGb = dataInGb;
    }

    public int getValidityInDays() {
        return validityInDays;
    }

    public void setValidityInDays(int validityInDays) {
        this.validityInDays = validityInDays;
    }
}
