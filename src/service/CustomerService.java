package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerService {
    private static CustomerService instance;
    private static Collection<Customer> allCustomers;

    private CustomerService() {
        allCustomers = new ArrayList<Customer>();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName) throws IllegalArgumentException {
        Customer newCustomer = new Customer(firstName, lastName, email);
        if (allCustomers.contains(newCustomer)) {
            throw new IllegalArgumentException("This customer is already added");
        }
        allCustomers.add(newCustomer);
    }

    public Customer getCustomer(String customerEmail) {
        for (Customer c : allCustomers) {
            if (c.getEmail().equals(customerEmail)) {
                return c;
            }
        }
        return null;
    }

    public Collection<Customer> getAllCustomers() {
        return allCustomers;
    }
}
