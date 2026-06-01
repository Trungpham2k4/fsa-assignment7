package dao;

import fa.training.dao.CustomerDAO;
import fa.training.dao.impl.CustomerDAOImpl;
import fa.training.entity.Customer;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class CustomerDAOTest {

    private CustomerDAO customerDAO;
    private final List<Integer> createdCustomerIds = new ArrayList<>();

    @BeforeEach
    public void setup(){
        customerDAO = new CustomerDAOImpl();
    }

    private Customer createTestCustomer() {

        Customer customer = new Customer();
        customer.setCustomerName("Test Customer");

        customerDAO.save(customer);

        Customer created =
                customerDAO.findAll()
                        .stream()
                        .filter(c ->
                                "Test Customer"
                                        .equals(c.getCustomerName()))
                        .reduce((first, second) -> second)
                        .orElseThrow();

        createdCustomerIds.add(
                created.getCustomerId());

        return created;
    }

    @AfterEach
    void cleanup() {

        for(Integer id : createdCustomerIds) {

            try {
                customerDAO.delete(id);
            } catch (Exception ignored) {
            }
        }

        createdCustomerIds.clear();
    }


    @Test
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
        Customer customer = createTestCustomer();
        customer.setCustomerName("Updated Customer");
        customerDAO.update(customer);

        Customer updateCustomer = customerDAO.findById(customer.getCustomerId());
        Assertions.assertEquals("Updated Customer", updateCustomer.getCustomerName());
    }

    @Test
    public void testDeleteCustomer(){
        Customer customer = createTestCustomer();
        customerDAO.delete(customer.getCustomerId());

        Customer deletedCustomer = customerDAO.findById(customer.getCustomerId());
        createdCustomerIds.remove(Integer.valueOf(customer.getCustomerId()));

        Assertions.assertNull(deletedCustomer);
    }

    @Test
    public void testFindAll(){
        createTestCustomer();
        List<Customer> customers = customerDAO.findAll();
        Assertions.assertNotNull(customers);
        Assertions.assertFalse(customers.isEmpty());
    }

    @Test
    public void testFindById(){
        Customer mock = createTestCustomer();
        Customer customer = customerDAO.findById(mock.getCustomerId());
        Assertions.assertNotNull(customer);
        Assertions.assertEquals(mock.getCustomerId(), customer.getCustomerId());
    }

}
