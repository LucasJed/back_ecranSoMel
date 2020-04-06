package com.livetree.ecransomel.back.Entities;

import javax.persistence.*;

/**
 * Cette classe représente l'objet dans la base de donnée
 */
@Entity
@Table(name = "TrendTable_Jour")
public class TrendTable_Jour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Chrono")
    private long chrono;

    @Column(name = "Name")
    private String name;

    @Column(name="Value")
    private float value;

    @Column(name="Quality")
    private int quality;

    @Column(name="Description")
    private String description;

    public TrendTable_Jour() { }

    public TrendTable_Jour(long chrono, String name, float value, int quality, String description) {
        this.chrono = chrono;
        this.name = name;
        this.value = value;
        this.quality = quality;
        this.description = description;
    }

    /**
     * Cette fonction a but de test
     *
     * @param name
     */
    public TrendTable_Jour(String name) {
        this.name = name;

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

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
