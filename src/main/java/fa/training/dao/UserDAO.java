package fa.training.dao;

import fa.training.dto.UserVO;
import fa.training.entity.User;

import java.util.List;

public interface UserDAO {
    void save(User user);
    void update(User user);
    void delete(int id);
    User findById(int id);
    List<User> findAll();
    List<UserVO> findAllUsersWithNumberOfTransactions();
}
