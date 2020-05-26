package com.sm.model;

import javax.persistence.*;

@Entity
public class SubSpecialization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long subSpecId;

    private String subSpecName;
    @ManyToOne
    private Specialization specialization;

    public long getSubSpecId() {
        return subSpecId;
    }

    public void setSubSpecId(long subSpecId) {
        this.subSpecId = subSpecId;
    }

    public String getSubSpecName() {
        return subSpecName;
    }

    public void setSubSpecName(String subSpecName) {
        this.subSpecName = subSpecName;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
