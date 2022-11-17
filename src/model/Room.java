package model;

import java.util.Objects;

public class Room implements IRoom{
    // Attributes
    protected String roomNumber;
    protected Double price;
    protected RoomType enumeration;

    // Constructor
    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.enumeration;
    }

    @Override
    public boolean isFree() {
        return Math.abs(price - 0) < 10e-5;
    }

    @Override
    public String toString() {
        String res = String.format("Room Number: %s %s Room Price: $%f\n",
                roomNumber, enumeration, price);
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration);
    }
}
