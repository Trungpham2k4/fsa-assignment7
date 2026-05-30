package fa.training.dao;

import fa.training.entity.Customer;

import java.util.List;

public interface CustomerDAO extends CommonDAO<Customer> {
    void save(Customer customer);
}
