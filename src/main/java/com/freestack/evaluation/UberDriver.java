package com.freestack.evaluation;

import javax.persistence.*;

@Entity
@Table(name = "uberdriver")
public class UberDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "available")
    private Boolean available;
    @Column(name = "fisrtname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;

    public UberDriver(){

    }
    public UberDriver(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.available = true;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
