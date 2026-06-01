package dao;

import fa.training.dao.UserDAO;
import fa.training.dao.impl.UserDAOImpl;
import fa.training.dto.UserVO;
import fa.training.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private UserDAO userDAO;
    private final List<Integer> createdUserIds = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        userDAO = new UserDAOImpl();
    }

    @AfterEach
    public void cleanup(){
        for(Integer userId : createdUserIds){
            userDAO.delete(userId);
        }
        createdUserIds.clear();
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
        User user = createTestUser();
        user.setUsername("admin_updated");
        userDAO.update(user);
        User updateUser = userDAO.findById(user.getUserId());
        assertEquals("admin_updated", updateUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        User user = createTestUser();
        userDAO.delete(user.getUserId());
        User deletedUser = userDAO.findById(user.getUserId());
        createdUserIds.remove(Integer.valueOf(user.getUserId()));
        assertNull(deletedUser);
    }

    @Test
    public void testFindUserById() {
        User mockUser = createTestUser();
        User foundUser = userDAO.findById(mockUser.getUserId());
        assertNotNull(foundUser);
        assertEquals(mockUser.getUserId(), foundUser.getUserId());
    }

    @Test
    public void testFindAll(){
        createTestUser();
        List<User> users = userDAO.findAll();
        assertFalse(users.isEmpty());
    }

    @Test
    public void testFindAllUsersWithNumberOfTransactions(){
        createTestUser();
        List<UserVO> users = userDAO.findAllUsersWithNumberOfTransactions();
        assertNotNull(users);
        assertFalse(users.isEmpty());
        for(UserVO user : users){
            System.out.println(user.toString());
        }
    }

}
