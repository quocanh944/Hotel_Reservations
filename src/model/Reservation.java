package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate, checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
            throws IllegalArgumentException {
        this.customer = customer;
        this.room = room;
        if (checkInDate.compareTo(checkOutDate) > 0) {
            throw new IllegalArgumentException("Checkin date must be before checkout date");
        }
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public final Customer getCustomer() {
        return customer;
    }

    public final IRoom getRoom() {
        return room;
    }

    public final Date getCheckInDate() {
        return checkInDate;
    }

    public final Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
        String checkInDate = formatter.format(this.checkInDate);
        String checkOutDate = formatter.format(this.checkOutDate);

        return String.format("Reservation\n" +
                "%s %s\n" +
                "Room: %s - %s\n" +
                "Price: $%f price per night\n" +
                "Checkin Date: %s\n" +
                "Checkout Date: %s",
                customer.getFirstName(), customer.getLastName(),
                room.getRoomNumber(), room.getRoomType(),
                room.getRoomPrice(),
                checkInDate,
                checkOutDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Reservation that = (Reservation) o;

        boolean check = customer.equals(that.customer) && room.equals(that.room);
        check = check && checkInDate.equals(that.checkInDate) && checkOutDate.equals(that.checkOutDate);
        System.out.println("Customer: " + customer.equals(that.customer));
        System.out.println("Room: " + room.equals(that.room));
        System.out.println("Checkin: " + checkInDate.equals(that.checkInDate));
        System.out.println("Checkout: " + checkOutDate.equals(that.checkOutDate));
        return check;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
