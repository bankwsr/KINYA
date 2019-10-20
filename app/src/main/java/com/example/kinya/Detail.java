package com.example.kinya;

public class Detail {
    private String detailId;
    private String detailName;

    public Detail(String detailId, String detailName) {
        this.detailId = detailId;
        this.detailName = detailName;
    }

    public String getDetailId() {
        return detailId;
    }

    public String getDetailName() {
        return detailName;
    }
}