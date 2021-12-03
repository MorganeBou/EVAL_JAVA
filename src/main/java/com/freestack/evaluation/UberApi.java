package com.freestack.evaluation;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UberApi {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "myEntityManager");


    public static void enrollUser(UberUser uberUser) {
        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {

            entityManager.getTransaction().begin();
            UberUser uberUserToEnroll = uberUser;
            entityManager.persist(uberUser);
            entityManager.getTransaction().commit();

        } finally {
            entityManager.close();
        }


    }

    public static void enrollDriver(UberDriver uberDriver) {
        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(uberDriver);
            entityManager.getTransaction().commit();

        } finally {
            entityManager.close();
        }


    }

    public static Booking bookOneDriver(UberUser uberUser) {

       EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();

       List<UberDriver> uberDriverList = (List<UberDriver>) entityManager
       .createQuery
       ("SELECT u From UberDriver u WHERE u.available = true").getResultList();


        if (uberDriverList.size() > 0) {
            entityManager.getTransaction().begin();
            UberDriver driver = uberDriverList.get(0);
            Booking booking = new Booking();
            booking.setUberUser(uberUser);
            booking.setStartofthebooking(LocalDateTime.now());
            booking.setUberDriver(driver);
            driver.setAvailable(false);
            entityManager.persist(booking);
            entityManager.merge(driver);

            entityManager.getTransaction().commit();
            entityManager.close();
            return booking;
        } else
            return null;



    }

    public static void finishBooking(Booking booking) {
        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            booking.setEndofthebooking(LocalDateTime.now());
            booking.getUberDriver().setAvailable(true);
            entityManager.merge(booking);
            entityManager.merge(booking.getUberDriver());
            entityManager.getTransaction().commit();

        } finally {
            entityManager.close();
        }


    }

    public static void evaluateDriver(Booking booking, Integer evaluation) {
        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            booking.setEvaluation(evaluation);
            entityManager.merge(booking);
            entityManager.getTransaction().commit();

        } finally {
            entityManager.close();
        }

    }

    public static List<Booking> listDriverBookings(UberDriver uberDriver) {

        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Booking> driverBookingList = new ArrayList<>();

            TypedQuery<Booking> allBooking = entityManager.createQuery(
                    "select " +
                            "b from Booking b ", Booking.class);

            List<Booking> bookingList = allBooking.getResultList();

            for (Booking booking : bookingList) {
                if (booking.getUberDriver().equals(uberDriver)) {
                    driverBookingList.add(booking);

                }
            }
            return bookingList;


        } finally {
            entityManager.close();
        }


    }

    public static List<Booking> listUnfinishedBookings() {


        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Booking> unfinishedBookingList = new ArrayList<>();

            TypedQuery<Booking> allBooking = entityManager.createQuery(
                    "SELECT b FROM Booking b WHERE b.endofthebooking IS NULL",
                    Booking.class);

            unfinishedBookingList = allBooking.getResultList();


            return unfinishedBookingList;

        } finally {

            entityManager.close();
        }

    }

    public static float meanScore(UberDriver uberDriver) {
        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query queryDriver = entityManager.createQuery("SELECT AVG(b" +
                    ".evaluation) FROM Booking b WHERE b.uberDriver= " +
                    ":uberdriver");

            queryDriver.setParameter("uberdriver", uberDriver);
            Double meanScoreToFind = (Double)queryDriver.getSingleResult();
            float meanScoreToFindInFloat = meanScoreToFind.floatValue();

            entityManager.getTransaction().commit();
            return meanScoreToFindInFloat;

        } finally {
            entityManager.close();
        }
    }
}


