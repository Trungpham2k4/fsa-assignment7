package dao;

import fa.training.dao.UserDAO;
import fa.training.dao.impl.UserDAOImpl;
import fa.training.dto.UserVO;
import fa.training.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        userDAO = new UserDAOImpl();
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("admin");
        user.setEmail("abc@gmail.com");
        user.setPoint(0);
        user.setDateOfBirth(LocalDate.parse("2000-01-01"));
        userDAO.save(user);

        List<User> users = userDAO.findAll();
        boolean foundUser = users.stream().anyMatch(u -> u.getEmail().equals("abc@gmail.com"));
        assertTrue(foundUser);
    }

    @Test
    public void testUpdateUser() {
        List<User> users = userDAO.findAll();
        if(users.isEmpty()){
            fail("No users found in database");
        }
        User user = users.getFirst();
        user.setUsername("admin_updated");
        userDAO.update(user);
        User updateUser = userDAO.findById(user.getUserId());
        assertEquals("admin_updated", updateUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        List<User> users = userDAO.findAll();
        User user = users.getFirst();
        userDAO.delete(user.getUserId());
        User deletedUser = userDAO.findById(user.getUserId());
        assertNull(deletedUser);
    }

    @Test
    public void testFindUserById() {
        List<User> users = userDAO.findAll();
        if(users.isEmpty()) {
            fail("No users found in the database.");
        }
        int id = users.getFirst().getUserId();
        User foundUser = userDAO.findById(id);
        assertNotNull(foundUser);
        assertEquals(id, foundUser.getUserId());
    }

    @Test
    public void testFindAll(){
        List<User> users = userDAO.findAll();
        assertFalse(users.isEmpty());
    }

    @Test
    public void testFindAllUsersWithNumberOfTransactions(){
        List<UserVO> users = userDAO.findAllUsersWithNumberOfTransactions();
        assertFalse(users.isEmpty());
        for(UserVO user : users){
            System.out.println(user.toString());
        }
    }

}
