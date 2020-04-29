package com.livetree.ecransomel.back.Entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Cette classe représente l'objet dans la base de donnée
 */


@Entity
@Table(name = "TrendTable_Jour")
public class TrendTable_Jour {

    @EmbeddedId MeasureId id;
    private long chrono;
    private String name;
    float value;
    short quality;
    @Column(name="Description")
    private String description;

    public TrendTable_Jour() { }

    public long getChrono() {
        return this.id.chrono;
    }

    public String getName() {
        return this.id.name;
    }

    public float getValue() {
        return this.id.value;
    }

    public int getQuality() {
        return this.id.quality;
    }

    public String getDescription() {
        return this.description;
    }

}

@Embeddable
class MeasureId implements Serializable {
    @Column(name = "Chrono")
    long chrono;
    
    @Column(name = "Name")
    String name;

    @Column(name="Value")
    float value;

    @Column(name="Quality")
    short quality;
}
