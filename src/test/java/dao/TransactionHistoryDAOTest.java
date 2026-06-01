package dao;

import fa.training.dao.CustomerDAO;
import fa.training.dao.TransactionHistoryDAO;
import fa.training.dao.UserDAO;
import fa.training.dao.impl.CustomerDAOImpl;
import fa.training.dao.impl.TransactionHistoryDAOImpl;
import fa.training.dao.impl.UserDAOImpl;
import fa.training.entity.Customer;
import fa.training.entity.TransactionHistory;
import fa.training.entity.User;
import fa.training.util.Constant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionHistoryDAOTest {
    private TransactionHistoryDAO transactionHistoryDAO;
    private UserDAO userDAO;
    private CustomerDAO customerDAO;
    private final List<Integer> transactionIds = new ArrayList<>();
    private final List<Integer> createdUserIds = new ArrayList<>();
    private final List<Integer> createdCustomerIds = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        transactionHistoryDAO = new TransactionHistoryDAOImpl();
        userDAO = new UserDAOImpl();
        customerDAO = new CustomerDAOImpl();
    }

    @AfterEach
    void cleanup() {

        for(Integer id : transactionIds) {
            transactionHistoryDAO.delete(id);
        }

        for(Integer id : createdUserIds) {
            userDAO.delete(id);
        }

        for(Integer id : createdCustomerIds) {
            customerDAO.delete(id);
        }

        transactionIds.clear();
        createdUserIds.clear();
        createdCustomerIds.clear();
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

    private User createTestUser(){
        User user = new User();
        user.setUsername("Test user");
        user.setEmail("test@gmail.com");
        user.setPoint(0);
        user.setDateOfBirth(LocalDate.parse("2000-01-01"));
        userDAO.save(user);

        User created =
                userDAO.findAll()
                        .stream()
                        .filter(u ->
                                "Test user"
                                        .equals(u.getUsername()))
                        .reduce((first, second) -> second)
                        .orElseThrow();
        createdUserIds.add(created.getUserId());
        return created;
    }


    private TransactionHistory createTransactionHistory() {
        User user = createTestUser();
        Customer customer = createTestCustomer();

        TransactionHistory tx = new TransactionHistory();

        tx.setUserId(user.getUserId());
        tx.setCustomerId(customer.getCustomerId());
        tx.setPoint(100);
        tx.setAction("IN");
        tx.setCreatedDateTime(LocalDateTime.now());

        int id = transactionHistoryDAO.save(tx);

        transactionIds.add(id);

        return transactionHistoryDAO.findById(id);
    }


    @Test
    public void testAddTransactionHistory() {
        User firstUser = createTestUser();
        Customer firstCustomer = createTestCustomer();
        String action = "IN";
        long point = 100;
        LocalDateTime created_date_time = LocalDateTime.parse("12-12-2020 04:12:12", Constant.DATE_TIME_FORMATTER);
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setUserId(firstUser.getUserId());
        transactionHistory.setCustomerId(firstCustomer.getCustomerId());
        transactionHistory.setPoint(point);
        transactionHistory.setAction(action);
        transactionHistory.setCreatedDateTime(created_date_time);
        int transactionId = transactionHistoryDAO.save(transactionHistory);
        assertTrue(transactionId > 0, "Transaction history ID should be greater than 0 after saving.");
    }

    @Test
    public void testDeleteTransactionHistory() {
        TransactionHistory firstTransactionHistory = createTransactionHistory();
        int transactionIdToDelete = firstTransactionHistory.getTransactionId();
        transactionHistoryDAO.delete(transactionIdToDelete);
        TransactionHistory deletedTransactionHistory = transactionHistoryDAO.findById(transactionIdToDelete);
        transactionIds.remove(Integer.valueOf(transactionIdToDelete));
        createdUserIds.remove(Integer.valueOf(firstTransactionHistory.getUserId()));
        createdCustomerIds.remove(Integer.valueOf(firstTransactionHistory.getCustomerId()));
        assertNull(deletedTransactionHistory, "Deleted transaction history should not be found in the database.");
    }

    @Test
    public void testUpdateTransactionHistory() {
        TransactionHistory firstTransactionHistory = createTransactionHistory();
        int transactionIdToUpdate = firstTransactionHistory.getTransactionId();
        firstTransactionHistory.setNote("Updated note");
        transactionHistoryDAO.update(firstTransactionHistory);
        TransactionHistory updatedTransactionHistory = transactionHistoryDAO.findById(transactionIdToUpdate);
        assertNotNull(updatedTransactionHistory, "Updated transaction history should be found in the database.");
        assertEquals("Updated note", updatedTransactionHistory.getNote(), "The note should be updated correctly in the database.");
    }

    @Test
    public void testFindTransactionHistoryById() {
        TransactionHistory firstTransactionHistory = createTransactionHistory();
        TransactionHistory foundTransactionHistory = transactionHistoryDAO.findById(firstTransactionHistory.getTransactionId());
        assertNotNull(foundTransactionHistory, "Transaction history should be found by ID.");
        assertEquals(firstTransactionHistory.getTransactionId(), foundTransactionHistory.getTransactionId(), "The transaction IDs should match.");
    }

    @Test
    public void testFindAllTransactionHistories() {
        createTransactionHistory();
        List<TransactionHistory> transactionHistories = transactionHistoryDAO.findAll();
        assertFalse(transactionHistories.isEmpty(), "Transaction history list should not be empty.");
    }

    @Test
    public void testFindTransactionHistoryByUserId() {
        TransactionHistory transaction = createTransactionHistory();
        int userId = transaction.getUserId();
        List<TransactionHistory> results = transactionHistoryDAO.findByUserId(userId);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.stream().allMatch(t -> t.getUserId() == userId));
    }


}
