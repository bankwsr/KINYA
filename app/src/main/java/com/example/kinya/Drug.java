package com.example.kinya;

public class Drug {
    private String drugId;
    private String drugName;
    private String drugDetail;
    private String drugType;

    public Drug(){
    }

    public Drug(String drugId, String drugName, String drugDetail, String drugType) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.drugDetail = drugDetail;
        this.drugType = drugType;
    }

    public String getDrugId() {
        return drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDrugDetail() {
        return drugDetail;
    }

    public String getDrugType() {
        return drugType;
    }
}

