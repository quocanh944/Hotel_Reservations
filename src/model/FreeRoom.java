package model;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);
    }

    @Override
    public String toString() {
        return String.format("Room Number: %s %s Room Price: free\n",
                roomNumber, enumeration);
    }
}
