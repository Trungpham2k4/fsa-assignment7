package dao;

import fa.training.dao.CustomerDAO;
import fa.training.dao.impl.CustomerDAOImpl;
import fa.training.entity.Customer;
import org.junit.jupiter.api.*;

import java.util.List;

public class CustomerDAOTest {

    private CustomerDAO customerDAO;
    @BeforeEach
    public void setup(){
        customerDAO = new CustomerDAOImpl();
    }

    @Test
    @Disabled
    public void testAddCustomer(){
        Customer customer = new Customer();
        customer.setCustomerName("John Doe");
        customerDAO.save(customer);

        List<Customer> customers = customerDAO.findAll();
        boolean found = customers.stream().anyMatch(c -> c.getCustomerName().equals(customer.getCustomerName()));
        Assertions.assertTrue(found);
    }

    @Test
    public void testUpdateCustomer(){
        List<Customer> customers = customerDAO.findAll();
        if(customers.isEmpty()){
            Assertions.fail("No customer found to update");
        }
        Customer customer = customers.getFirst();
        customer.setCustomerName("Trung Pham");
        customerDAO.update(customer);

        Customer updateCustomer = customerDAO.findById(customer.getCustomerId());
        Assertions.assertEquals("Trung Pham", updateCustomer.getCustomerName());
    }

    @Test
    public void testDeleteCustomer(){
        List<Customer> customers = customerDAO.findAll();
        if(customers.isEmpty()){
            Assertions.fail("No customer found to delete");
        }
        int id = customers.getFirst().getCustomerId();
        customerDAO.delete(id);

        Customer deletedCustomer = customerDAO.findById(id);
        Assertions.assertNull(deletedCustomer);
    }

    @Test
    public void testFindAll(){
        List<Customer> customers = customerDAO.findAll();
        Assertions.assertFalse(customers.isEmpty());
    }

    @Test
    public void testFindById(){
        List<Customer> customers = customerDAO.findAll();
        if(customers.isEmpty()){
            Assertions.fail("No customer found");
        }
        int id = customers.getFirst().getCustomerId();
        Customer customer = customerDAO.findById(id);
        Assertions.assertNotNull(customer);
        Assertions.assertEquals(id, customer.getCustomerId());
    }

}
