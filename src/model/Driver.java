package model;

import java.util.Date;

public class Driver {
    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "j@gmail.com");
        IRoom room = new Room("100", 0.1, RoomType.DOUBLE);
        Date checkIn = new Date(2022, 11, 11);
        Date checkOut1 = new Date(2022, 11, 11);
        checkOut1.setDate(checkIn.getDate() + 31);
        Date checkOut2 = new Date(2022, 11, 11);
        checkOut2.setDate(checkIn.getDate() + 30);

        Reservation re1 = new Reservation(customer, room, checkIn, checkOut1);
        Reservation re2 = new Reservation(customer, room, checkIn, checkOut2);
        System.out.println(re1.equals(re2));
    }
}
