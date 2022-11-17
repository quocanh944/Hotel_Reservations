package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ReservationService {
    private static ReservationService instance;
    private static Collection<Reservation> allReservations;
    private static Collection<IRoom> allRooms;

    private ReservationService() {
        allReservations = new ArrayList<Reservation>();
        allRooms = new ArrayList<IRoom>();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public void addRoom(IRoom room) throws IllegalArgumentException {
        if (allRooms.contains(room)) {
            throw new IllegalArgumentException("This room is already added");
        }
        allRooms.add(room);
    }

    public IRoom getARoom (String roomId) {
        for (IRoom r : allRooms) {
            if (r.getRoomNumber().equals(roomId)) {
                return r;
            }
        }
        return null;
    }

    private static Collection<Reservation> getAllReservationsByRoomID(String roomID) {
        Collection<Reservation> res = new ArrayList<Reservation>();
        for (Reservation reservation : allReservations) {
            if (reservation.getRoom().getRoomNumber().equals(roomID)) {
                res.add(reservation);
            }
        }
        return res;
    }

    private static boolean checkTwoDateRangesOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {

        return !(endDate1.before(startDate2) || startDate1.after(endDate2));
    }

    // This method support to check room can be rent or not
    private boolean checkAvailableRoom(IRoom room, Date checkInDate, Date checkOutDate) {
        Collection<Reservation> allRoomReservations = getAllReservationsByRoomID(room.getRoomNumber());
        for (Reservation reservation : allRoomReservations) {
            if (checkTwoDateRangesOverlap(
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate(),
                    checkInDate,
                    checkOutDate)) {
                return false;
            }
        }
        return true;
    }

    public Reservation reserveARoom(Customer customer, IRoom room,
                                    Date checkInDate, Date checkOutDate)
            throws IllegalArgumentException {
        if (checkAvailableRoom(room, checkInDate, checkOutDate)) {
            Reservation res = new Reservation(customer, room, checkInDate, checkOutDate);
            allReservations.add(res);
            return res;
        } else {
            throw new IllegalArgumentException("Date Range Overlap");
        }
    }

    public Collection<IRoom> findRooms (Date checkInDate, Date checkOutDate) {
        Collection<IRoom> res = new ArrayList<IRoom>();
        for (IRoom room : allRooms) {
            if (checkAvailableRoom(room, checkInDate, checkOutDate)) {
                res.add(room);
            }
        }
        return res;
    }

    public Collection<Reservation> getCustomersReservations(Customer customer) {
        Collection<Reservation> res = new ArrayList<Reservation>();
        for (Reservation reservation : allReservations) {
            if (reservation.getCustomer().equals(customer)) {
                res.add(reservation);
            }
        }
        return res;
    }

    public void printAllReservations() {
        if (allReservations.isEmpty()) {
            System.out.println("Has no reservation");
            return;
        }
        for (Reservation reservation : allReservations) {
            System.out.println(reservation);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return allRooms;
    }
}
