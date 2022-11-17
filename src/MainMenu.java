import api.HotelResource;
import model.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner sc = new Scanner(System.in);

    private static Date inputDate(String s) {
        System.out.println(s);
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Using parse method to convert the string to LocalDate object
            LocalDate tmp = LocalDate.parse(sc.next(), format);

            // Printing the date object
            return Date.from(tmp.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception ex) {
            System.out.println("Date is not valid");
        }
        return null;
    }

    private static Date[] input2Date() {
        Date checkInDate, checkOutDate;
        do {
            checkInDate = inputDate("Enter your checkin date in the format: dd/mm/yyyy");
            while (checkInDate == null) {
                checkInDate = inputDate("Enter your checkin date in the format: dd/mm/yyyy");
            }
            checkOutDate = inputDate("Enter your checkout date in the format: dd/mm/yyyy");
            while (checkOutDate == null) {
                checkOutDate = inputDate("Enter your checkout date in the format: dd/mm/yyyy");
            }

            if (checkInDate.after(checkOutDate)) {
                System.out.println("Checkin date must before checkout date.");
                System.out.println("_______________________________________");
            }
        } while (checkInDate.after(checkOutDate));
        return new Date[]{checkInDate, checkOutDate};
    }

    private static Customer getCustomerFromInputEmail() {
        String email;
        Customer customer;
        while (true) {
            System.out.print("Enter your email (Q for exit): ");
            email = sc.next();
            if (email.equalsIgnoreCase("Q")) {
                return null;
            }
            customer = hotelResource.getCustomer(email);
            if (customer == null) {
                System.out.println("Email not found.");
            } else {
                break;
            }
        }
        return customer;
    }

    private static Customer createAccount() {
        System.out.println("Please input your first name:");
        String firstName = sc.next();
        System.out.println("Last name:");
        String lastName = sc.next();
        String email;
        while (true) {
            System.out.println("Email: (format name@domain.com)");
            email = sc.next();
            if (!email.matches(Customer.VALID_EMAIL_ADDRESS_REGEX)) {
                System.out.println("Format wrong. Enter it again.");
            } else {
                break;
            }
        }
        try {
            hotelResource.createACustomer(email, firstName, lastName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Welcome to the Hotel Reservation Application");
        return hotelResource.getCustomer(email);
    }

    private static void findRooms() {
        Date[] tmp = input2Date();
        Date checkInDate = tmp[0], checkOutDate = tmp[1];

        String pattern = "EEE MMM dd yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        System.out.println(String.format("Checkin Date: %s Checkout Date: %s",
                simpleDateFormat.format(checkInDate),
                simpleDateFormat.format(checkOutDate)));

        Collection<IRoom> availRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if (availRooms.isEmpty()) {
            checkInDate.setDate(checkInDate.getDate() + 7);
            checkOutDate.setDate(checkOutDate.getDate() + 7);
            availRooms = hotelResource.findARoom(checkInDate, checkOutDate);
            if (!availRooms.isEmpty()) {
                System.out.println("All rooms are unavailable.\n" +
                        "I suggest room for next week from your choice.");
            }
        }
        System.out.println("Available Rooms:");
        System.out.println(availRooms);
    }

    private static void reserveARoom() {
        Customer customer;
        Date[] tmp = input2Date();
        String roomId;
        Date checkInDate = tmp[0], checkOutDate = tmp[1];
        Collection<IRoom> availRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if (availRooms.isEmpty()) {
            System.out.println("No room available.");
            return;
        }

        System.out.println("Please input room number:");
        roomId = sc.next();
        if (hotelResource.getRoom(roomId) == null) {
            System.out.println("No room found.");
            return;
        }
        while (true) {
            System.out.println("Do you have account: Y/N");
            String answer = sc.next();
            if (answer.equalsIgnoreCase("Y")) {
                customer = getCustomerFromInputEmail();
            } else if (answer.equalsIgnoreCase("N")) {
                System.out.println("You need to create account for your reservations.");
                customer = createAccount();
            } else {
                continue;
            }
            try {
                System.out.println(hotelResource.bookARoom(customer.getEmail(), hotelResource.getRoom(roomId), checkInDate, checkOutDate));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            break;
        }

    }

    private static void findAndReserveARoom() {
        int choice;
        while (true) {
            try {
                do {
                    System.out.println(" ___________________________________________________");
                    System.out.println("1." + "Find a Room.");
                    System.out.println("2." + "Reserve a Room.");
                    System.out.println("3." + "Exit.");
                    choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            findRooms();
                            break;
                        case 2:
                            reserveARoom();
                            break;
                        case 3:
                            return;
                    }
                } while (true);
            } catch (Exception ex) {
                System.out.println("Error: Invalid Input");
                sc.next();
            }
        }
    }

    private static void showCustomerReservations() {
        System.out.println("Please input your email:");
        String email = sc.next();
        System.out.println(hotelResource.getCustomersReservations(email));
    }

    public static void displayMenu() {
        System.out.println("\n Welcome to the Hotel Reservation Application ");
        System.out.println(" ___________________________________________________");
        System.out.println("1." + "Find and Reserve a Room");
        System.out.println("2." + "See my Reservations");
        System.out.println("3." + "Create an Account");
        System.out.println("4." + "Admin");
        System.out.println("5." + "Exit");
        System.out.println(" ___________________________________________________");
        System.out.println("Please select one of the options");
    }

    public static void start() {
//        loadData();
        int choice, flag = 0;
        while (flag == 0) {
            try {
                do {
                    displayMenu();
                    choice = sc.nextInt();
                    switch (choice) {
                        case 1 -> findAndReserveARoom();
                        case 2 -> showCustomerReservations();
                        case 3 -> createAccount();
                        case 4 -> AdminMenu.start(sc);
                        case 5 -> System.out.println("Thanks");
                    }
                } while (choice != 5);
                flag = 1;
            } catch (Exception ex) {
                System.out.println("Error: Invalid Input");
                sc.next();
            }
        }
        sc.close();
    }

}
