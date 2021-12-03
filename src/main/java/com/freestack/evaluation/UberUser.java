package com.freestack.evaluation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "uberuser")
public class UberUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "fisrtname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;


    public UberUser(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public UberUser(){}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
