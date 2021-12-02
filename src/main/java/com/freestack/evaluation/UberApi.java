package com.freestack.evaluation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            entityManager.persist(uberUserToEnroll);

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
        // s'il y a un driver de libre
        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Booking booking = new Booking();
            booking.setUberUser(uberUser);
            booking.setStartofthebooking(LocalDateTime.now());

            TypedQuery<UberDriver> allDriver = entityManager.createQuery(
                    "select " +
                            "u from UberDriver u ", UberDriver.class);

            List<UberDriver> uberDriverList = allDriver.getResultList();

            for (UberDriver driver : uberDriverList) {
                if (driver.getAvailable().equals(true)) {
                    booking.setUberDriver(driver);
                    driver.setAvailable(false);
                    break;
                }
            }
            entityManager.persist(booking);
            entityManager.getTransaction().commit();
            return booking;

        } finally {
            entityManager.close();
        }

    }

    public static void finishBooking(Booking booking) {
        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            booking.setEndofthebooking(LocalDateTime.now());
            booking.getUberDriver().setAvailable(true);
            entityManager.persist(booking);
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
            entityManager.persist(booking);
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
            //entityManager.persist(bookingList);
            // entityManager.getTransaction().commit();

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
                    "select " +
                            "b from Booking b ", Booking.class);

            List<Booking> bookingList = allBooking.getResultList();

            for (Booking booking : bookingList) {
                if (booking.getEndofthebooking().equals(null)) {
                    unfinishedBookingList.add(booking);

                }
            }
            return unfinishedBookingList;

        } finally {
            entityManager.close();
        }

    }

    public static Integer meanScore(UberDriver uberDriver) {
        EntityManager entityManager = EntityManagerFactorySingleton
                .getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Booking> listDriverBookingsMeanScore =
                    listDriverBookings(uberDriver);
            Integer meanScoreToFind = 0;
            Integer numberOfEval = 0;

            for (Booking booking : listDriverBookingsMeanScore) {
                meanScoreToFind += booking.getEvaluation();
                numberOfEval++;
            }
            meanScoreToFind = meanScoreToFind / numberOfEval;

            return meanScoreToFind;

        } finally {
            entityManager.close();
        }
    }
}


