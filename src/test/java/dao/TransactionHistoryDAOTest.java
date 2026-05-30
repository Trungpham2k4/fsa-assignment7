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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionHistoryDAOTest {
    private TransactionHistoryDAO transactionHistoryDAO;
    private UserDAO userDAO;
    private CustomerDAO customerDAO;

    @BeforeEach
    public void setUp() {
        transactionHistoryDAO = new TransactionHistoryDAOImpl();
        userDAO = new UserDAOImpl();
        customerDAO = new CustomerDAOImpl();
    }

    @Test
    public void testAddTransactionHistory() {
        List<User> users = userDAO.findAll();
        if(users.isEmpty()){
            fail("No users found in the database. Please add a user before running this test.");
        }
        List<Customer> customers = customerDAO.findAll();
        if(customers.isEmpty()){
            fail("No customers found in the database. Please add a customer before running this test.");
        }
        User firstUser = users.getFirst();
        Customer firstCustomer = customers.getFirst();
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
        List<TransactionHistory> transactionHistories = transactionHistoryDAO.findAll();
        if(transactionHistories.isEmpty()){
            fail("No transaction histories found in the database. Please add a transaction history before running this test.");
        }
        TransactionHistory firstTransactionHistory = transactionHistories.getFirst();
        int transactionIdToDelete = firstTransactionHistory.getTransactionId();
        transactionHistoryDAO.delete(transactionIdToDelete);
        TransactionHistory deletedTransactionHistory = transactionHistoryDAO.findById(transactionIdToDelete);
        assertNull(deletedTransactionHistory, "Deleted transaction history should not be found in the database.");
    }

    @Test
    public void testUpdateTransactionHistory() {
        List<TransactionHistory> transactionHistories = transactionHistoryDAO.findAll();
        if(transactionHistories.isEmpty()){
            fail("No transaction histories found in the database. Please add a transaction history before running this test.");
        }
        TransactionHistory firstTransactionHistory = transactionHistories.getFirst();
        int transactionIdToUpdate = firstTransactionHistory.getTransactionId();
        firstTransactionHistory.setNote("Updated note");
        transactionHistoryDAO.update(firstTransactionHistory);
        TransactionHistory updatedTransactionHistory = transactionHistoryDAO.findById(transactionIdToUpdate);
        assertNotNull(updatedTransactionHistory, "Updated transaction history should be found in the database.");
        assertEquals("Updated note", updatedTransactionHistory.getNote(), "The note should be updated correctly in the database.");
    }

    @Test
    public void testFindTransactionHistoryById() {
        List<TransactionHistory> transactionHistories = transactionHistoryDAO.findAll();
        if(transactionHistories.isEmpty()){
            fail("No transaction histories found in the database. Please add a transaction history before running this test.");
        }
        TransactionHistory firstTransactionHistory = transactionHistories.getFirst();
        TransactionHistory foundTransactionHistory = transactionHistoryDAO.findById(firstTransactionHistory.getTransactionId());
        assertNotNull(foundTransactionHistory, "Transaction history should be found by ID.");
        assertEquals(firstTransactionHistory.getTransactionId(), foundTransactionHistory.getTransactionId(), "The transaction IDs should match.");
        assertEquals(firstTransactionHistory.getUserId(), foundTransactionHistory.getUserId(), "The user IDs should match.");
        assertEquals(firstTransactionHistory.getCustomerId(), foundTransactionHistory.getCustomerId(), "The customer IDs should match.");
        assertEquals(firstTransactionHistory.getPoint(), foundTransactionHistory.getPoint(), "The points should match.");
        assertEquals(firstTransactionHistory.getAction(), foundTransactionHistory.getAction(), "The actions should match.");
        assertEquals(firstTransactionHistory.getCreatedDateTime(), foundTransactionHistory.getCreatedDateTime(), "The created date times should match.");
    }

    @Test
    public void testFindAllTransactionHistories() {
        List<TransactionHistory> transactionHistories = transactionHistoryDAO.findAll();
        assertFalse(transactionHistories.isEmpty(), "Transaction history list should not be empty.");
    }

    @Test
    public void testFindTransactionHistoryByUserId() {
        List<User> users = userDAO.findAll();
        if(users.isEmpty()){
            fail("No users found in the database. Please add a user before running this test.");
        }
        User firstUser = users.getFirst();
        List<TransactionHistory> transactionHistories = transactionHistoryDAO.findByUserId(firstUser.getUserId());
        assertFalse(transactionHistories.isEmpty(), "Transaction history list for user ID " + firstUser.getUserId() + " should not be empty.");
    }


}
