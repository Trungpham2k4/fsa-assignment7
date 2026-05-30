package fa.training.dao.impl;

import fa.training.dao.UserDAO;
import fa.training.dto.UserVO;
import fa.training.entity.User;
import fa.training.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public void save(User user) {
        String sql = "INSERT INTO dbo.user(username, email, point, date_of_birth) VALUES (?,?,?,?)";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setLong(3, user.getPoint());
            preparedStatement.setDate(4, java.sql.Date.valueOf(user.getDateOfBirth()));
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE dbo.user SET username = ?, email = ?, point = ?, date_of_birth = ? WHERE user_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setLong(3, user.getPoint());
            preparedStatement.setDate(4, java.sql.Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setInt(5, user.getUserId());
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM dbo.user WHERE user_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM dbo.user WHERE user_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapToEntity(resultSet);
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM dbo.user";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = mapToEntity(resultSet);
                users.add(user);
            }
            return users;
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return List.of();
    }

    public List<UserVO> findAllUsersWithNumberOfTransactions(){
        String sql = """
                SELECT user_id, username, COUNT(*) as totalTransaction FROM dbo.user 
                JOIN dbo.transaction_history ON dbo.user.user_id = dbo.transaction_history.user_id
                GROUP BY user_id, username
                ORDER BY totalTransaction DESC
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            List<UserVO> userVOList = new ArrayList<>();
            while (resultSet.next()) {
                UserVO userVO = new UserVO(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                userVOList.add(userVO);
            }
            return userVOList;
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return List.of();
    }

    private User mapToEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt(1));
        user.setUsername(resultSet.getString(2));
        user.setEmail(resultSet.getString(3));
        user.setPoint(resultSet.getLong(4));
        user.setDateOfBirth(resultSet.getDate(5).toLocalDate());
        return user;
    }
}
