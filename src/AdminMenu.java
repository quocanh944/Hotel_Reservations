import api.AdminResource;
import api.HotelResource;
import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class AdminMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void displayMenu() {
        System.out.println("\n Admin Menu");
        System.out.println(" ___________________________________________________");
        System.out.println("1." + "See all Customers");
        System.out.println("2." + "See all Rooms");
        System.out.println("3." + "See all Reservations");
        System.out.println("4." + "Add a Room");
        System.out.println("5." + "Add a Test Data");
        System.out.println("6." + "Back to the menu");
        System.out.println(" ___________________________________________________");
        System.out.println("Please select one of the options");
    }

    private static void loadData() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./src/dataTest/customer.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] tmp = line.split(",");
                String firstName = tmp[0], lastName = tmp[1], email = tmp[2];
                hotelResource.createACustomer(email, firstName, lastName);
                // read next line
                line = reader.readLine();
            }
            reader.close();
            reader = new BufferedReader(new FileReader("./src/dataTest/room.txt"));
            line = reader.readLine();
            while (line != null) {
                String[] tmp = line.split(",");
                String roomNumber = tmp[0];
                Double price = Double.parseDouble(tmp[1]);
                RoomType roomType = tmp[2].equals("1") ? RoomType.SINGLE : RoomType.DOUBLE;
                if (price.equals(0.0)) {
                    adminResource.addRoom(new FreeRoom(roomNumber, roomType));
                } else {
                    adminResource.addRoom(new Room(roomNumber, price, roomType));
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Data input");
    }

    private static void showAllCustomers() {
        Collection<Customer> allCustomers = adminResource.getAllCustomers();
        if (allCustomers.isEmpty()) {
            System.out.println("Has no customer");
            return;
        }
        for (Customer customer : allCustomers) {
            System.out.println(customer);
        }
    }

    private static void showAllRooms() {
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        if (allRooms.isEmpty()) {
            System.out.println("Has no room");
            return;
        }
        for (IRoom r : allRooms) {
            System.out.println(r);
        }
    }

    private static void showAllReservations() {
        adminResource.displayAllReservations();
    }

    private static void addARoom(Scanner sc) {
        System.out.print("Please input room number:");
        String roomNumber = sc.next();
        Double price;
        RoomType type;
        while (true) {
            try {
                System.out.print("Price: ");
                price = sc.nextDouble();
                System.out.println("Number of bed: (1 or 2)");
                String noBed = sc.next();
                if (noBed.equals("1")) {
                    type = RoomType.SINGLE;
                } else if (noBed.equals("2")) {
                    type = RoomType.DOUBLE;
                } else {
                    throw new Exception("Number bed just 1 or 2");
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            if (price.equals(0.0)) {
                adminResource.addRoom(new FreeRoom(roomNumber, type));
            } else {
                adminResource.addRoom(new Room(roomNumber, price, type));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void start(Scanner sc) {
        int choice, flag = 0;
        while (flag == 0) {
            try {
                do {
                    displayMenu();
                    choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            showAllCustomers();
                            break;
                        case 2:
                            showAllRooms();
                            break;
                        case 3:
                            showAllReservations();
                            break;
                        case 4:
                            addARoom(sc);
                            break;
                        case 5:
                            loadData();
                            break;
                        case 6:
                            return;
                    }
                } while (choice != 6);
                flag = 1;
            } catch (Exception ex) {
                System.out.println("Error: Invalid Input");
                sc.next();
            }
        }
    }

}
