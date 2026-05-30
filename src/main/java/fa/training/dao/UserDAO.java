package fa.training.dao;

import fa.training.dto.UserVO;
import fa.training.entity.User;

import java.util.List;

public interface UserDAO extends CommonDAO<User> {
    void save(User user);
    List<UserVO> findAllUsersWithNumberOfTransactions();
}
