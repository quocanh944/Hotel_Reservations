package model;

import java.util.Objects;

public class Customer {
    private String firstName, lastName, email;
    public final static String VALID_EMAIL_ADDRESS_REGEX = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";

    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException{
        this.firstName = firstName;
        this.lastName = lastName;
        if (email.matches(VALID_EMAIL_ADDRESS_REGEX)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email does not match.");
        }
    }

    public final String getFirstName() {
        return this.firstName;
    }

    public final String getLastName() {
        return this.lastName;
    }

    public final String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return String.format("First Name: %s Last Name: %s Email: %s\n",
                firstName, lastName, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
