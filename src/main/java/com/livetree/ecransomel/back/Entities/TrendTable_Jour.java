package com.livetree.ecransomel.back.Entities;

import javax.persistence.*;

/**
 * Cette classe représente l'objet dans la base de donnée
 */
@Entity
public class TrendTable_Jour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private long chrono;

    @Column
    private String name;

    @Column
    private float value;

    @Column
    private float quality;

    @Column
    private String description;

    public TrendTable_Jour() {this.value=0;
    }

    public TrendTable_Jour(long chrono, String name, float value, float quality, String description) {
        this.chrono = chrono;
        this.name = name;
        this.value = value;
        this.quality = quality;
        this.description = description;
    }

    /**
     * Cette fonction a but de test
     * @param name
     */
    public TrendTable_Jour(String name) {
        this.name = name;
        this.value = 1;
        this.chrono = (132279065400000000L);

    }

    /**
     * Cette fonction a but de test
     * @param name
     * @param value
     */
    public TrendTable_Jour(String name, float value) {
        this.chrono = (132279065400000000L);
        this.name = name;
        this.value = value;
    }

    //Getter and setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getChrono() {
        return chrono;
    }

    public void setChrono(long chrono) {
        this.chrono = chrono;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
