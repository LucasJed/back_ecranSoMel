package com.livetree.ecransomel.back.Entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TrendTable_api {



    private String nomBatiment;
    private float production;
    private float consumption;
    private Timestamp timestamp;

    /**
     * Constructeur
     *
     * @param nomBatiment
     * @param production
     * @param consumption
     * @param timestamp
     */
    public TrendTable_api(String nomBatiment, float production, float consumption, Timestamp timestamp) {
        this.nomBatiment = nomBatiment;
        this.production = production;
        this.consumption = consumption;
        this.timestamp = timestamp;
    }

    /**
     * No arg Consttructeur
     */
    public TrendTable_api() {
    }




    public static ArrayList<TrendTable_api> createListOfDay() {
        return new ArrayList<TrendTable_api>();
    }




    public String getNomBatiment() {
        return nomBatiment;
    }

    public void setNomBatiment(String nomBatiment) {
        this.nomBatiment = nomBatiment;
    }

    public float getProduction() {
        return production;
    }

    public void setProduction(float production) {
        this.production = production;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
