package fa.training.service;

import fa.training.dao.CustomerDAO;
import fa.training.dao.impl.CustomerDAOImpl;
import fa.training.entity.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    public List<Customer> getAllCustomer() {
        return customerDAO.findAll();
    }
    public Customer getCustomerById(int id) {
        return customerDAO.findById(id);
    }
    public void addCustomer(Customer customer) {
        customerDAO.save(customer);
    }
    public boolean updateCustomer(Customer customer) {
        Customer updateCustomer = customerDAO.findById(customer.getCustomerId());
        if (updateCustomer != null) {
            customerDAO.update(customer);
            return true;
        }
        return false;
    }
    public void deleteCustomer(int id) {
        customerDAO.delete(id);
    }
}
