package fa.training.service;

import fa.training.dao.UserDAO;
import fa.training.dao.impl.UserDAOImpl;
import fa.training.dto.UserVO;
import fa.training.entity.User;

import java.util.List;

public class UserService {
    private final UserDAO userDAO = new UserDAOImpl();
    public void saveUser(User user) {
        userDAO.save(user);
    }
    public boolean updateUser(User user) {
        User oldUser = getUser(user.getUserId());
        if(oldUser == null) {
            return false;
        }
        user.setPoint(oldUser.getPoint());
        userDAO.update(user);
        return true;
    }
    public void deleteUser(int id) {
        userDAO.delete(id);
    }
    public User getUser(int id) {
        return userDAO.findById(id);
    }
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public List<UserVO> getTotalTransactionForUser(){
        return userDAO.findAllUsersWithNumberOfTransactions();
    }
}
