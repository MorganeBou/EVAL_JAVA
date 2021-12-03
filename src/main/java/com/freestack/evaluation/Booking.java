package com.freestack.evaluation;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "end_of_the_booking")
    private LocalDateTime endofthebooking;
    @Column(name = "evaluation")
    private Integer evaluation;
    @Column(name = "start_of_the_booking")
    private LocalDateTime startofthebooking;

    @ManyToOne
    @JoinColumn(name="uberdriver_id")
    private UberDriver uberDriver;


    @ManyToOne
    @JoinColumn(name = "uberuser_id")
    private UberUser uberUser;

    public Booking() {

    }

    public UberUser getUberUser() {
        return uberUser;
    }

    public void setUberUser(UberUser uberUser) {
        this.uberUser = uberUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getEndofthebooking() {
        return endofthebooking;
    }

    public void setEndofthebooking(LocalDateTime endofthebooking) {
        this.endofthebooking = endofthebooking;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public LocalDateTime getStartofthebooking() {
        return startofthebooking;
    }

    public void setStartofthebooking(LocalDateTime startofthebooking) {
        this.startofthebooking = startofthebooking;
    }

    public UberDriver getUberDriver() {
        return uberDriver;
    }

    public void setUberDriver(UberDriver uberDriver) {
        this.uberDriver = uberDriver;
    }

}
