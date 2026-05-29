package fa.training.dao;

import fa.training.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> findAll();
    Customer findById(int id);
    void save(Customer customer);
    void update(Customer customer);
    void delete(int id);
}
