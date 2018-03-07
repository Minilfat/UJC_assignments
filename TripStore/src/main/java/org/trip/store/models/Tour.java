package org.trip.store.models;

import java.time.LocalDate;


public class Tour {

    private long id;
    private double price;
    private String city;
    private LocalDate from;
    private LocalDate to;

    public Tour(long id, double price, String city, LocalDate from, LocalDate to) {
        this.id = id;
        this.price = price;
        this.city = city;
        this.from = from;
        this.to = to;
    }

    public Tour(double price, String city, LocalDate from, LocalDate to) {
        this.price = price;
        this.city = city;
        this.from = from;
        this.to = to;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }


    @Override
    public boolean equals(Object obj) {

        if ((obj == null) || !(obj instanceof Tour))
            return false;

        Tour other = (Tour) obj;

        return other.getCity().equals(this.city)
                && other.getTo().equals(this.to)
                && other.getFrom().equals(this.from)
                && other.getPrice() == this.price
                && other.getId() == this.id;

    }
}
