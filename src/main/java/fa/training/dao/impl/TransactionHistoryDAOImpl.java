package fa.training.dao.impl;

import fa.training.dao.TransactionHistoryDAO;
import fa.training.entity.TransactionHistory;
import fa.training.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryDAOImpl implements TransactionHistoryDAO {

    @Override
    public void save(TransactionHistory transactionHistory) {
        String sql = "CALL dbo.insert_transaction(?,?,?,?,?,?)";
        try(Connection connection = ConnectionManager.getConnection();
            CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setInt(1, transactionHistory.getUserId());
            callableStatement.setInt(2, transactionHistory.getCustomerId());
            callableStatement.setLong(3, transactionHistory.getPoint());
            callableStatement.setString(4, transactionHistory.getAction());
            callableStatement.setString(5, transactionHistory.getNote());
            callableStatement.setTimestamp(6, Timestamp.valueOf(transactionHistory.getCreatedDateTime()));
            callableStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void update(TransactionHistory transactionHistory) {
            String sql = "UPDATE dbo.transaction_history SET user_id = ?, customer_id = ?, point = ?, action = ?, note = ?, created_date_time = ? WHERE transaction_id = ?";
            try(Connection connection = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, transactionHistory.getUserId());
                preparedStatement.setInt(2, transactionHistory.getCustomerId());
                preparedStatement.setLong(3, transactionHistory.getPoint());
                preparedStatement.setString(4, transactionHistory.getAction());
                preparedStatement.setString(5, transactionHistory.getNote());
                preparedStatement.setTimestamp(6, Timestamp.valueOf(transactionHistory.getCreatedDateTime()));
                preparedStatement.setInt(7, transactionHistory.getTransactionId());
                preparedStatement.executeUpdate();
            }catch (SQLException e){
                System.out.println("Error: " + e);
            }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM dbo.transaction_history WHERE transaction_id = ?";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error: " + e);
        }
    }

    @Override
    public TransactionHistory findById(int id) {
        String sql = "SELECT * FROM dbo.transaction_history WHERE transaction_id = ?";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                TransactionHistory transactionHistory = new TransactionHistory();
                transactionHistory.setTransactionId(id);
                transactionHistory.setUserId(resultSet.getInt("user_id"));
                transactionHistory.setCustomerId(resultSet.getInt("customer_id"));
                transactionHistory.setPoint(resultSet.getLong("point"));
                transactionHistory.setAction(resultSet.getString("action"));
                transactionHistory.setNote(resultSet.getString("note"));
                transactionHistory.setCreatedDateTime(resultSet.getTimestamp("created_date_time").toLocalDateTime());
                return transactionHistory;
            }
        }catch (SQLException e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public List<TransactionHistory> findAll() {
        String sql = "SELECT * FROM dbo.transaction_history";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TransactionHistory> transactionHistoryList = new ArrayList<>();
            while (resultSet.next()) {
                TransactionHistory transactionHistory = new TransactionHistory();
                transactionHistory.setTransactionId(resultSet.getInt("transaction_id"));
                transactionHistory.setUserId(resultSet.getInt("user_id"));
                transactionHistory.setCustomerId(resultSet.getInt("customer_id"));
                transactionHistory.setPoint(resultSet.getLong("point"));
                transactionHistory.setAction(resultSet.getString("action"));
                transactionHistory.setNote(resultSet.getString("note"));
                transactionHistory.setCreatedDateTime(resultSet.getTimestamp("created_date_time").toLocalDateTime());
                transactionHistoryList.add(transactionHistory);
            }
            return transactionHistoryList;
        }catch (SQLException e){
            System.out.println("Error: " + e);
        }
        return List.of();
    }

    @Override
    public List<TransactionHistory> findByUserId(int userId) {
        String sql = "SELECT * FROM dbo.transaction_history WHERE user_id = ?";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TransactionHistory> transactionHistoryList = new ArrayList<>();
            while (resultSet.next()) {
                TransactionHistory transactionHistory = new TransactionHistory();
                transactionHistory.setTransactionId(resultSet.getInt("transaction_id"));
                transactionHistory.setUserId(resultSet.getInt("user_id"));
                transactionHistory.setCustomerId(resultSet.getInt("customer_id"));
                transactionHistory.setPoint(resultSet.getLong("point"));
                transactionHistory.setAction(resultSet.getString("action"));
                transactionHistory.setNote(resultSet.getString("note"));
                transactionHistory.setCreatedDateTime(resultSet.getTimestamp("created_date_time").toLocalDateTime());
                transactionHistoryList.add(transactionHistory);
            }
            return transactionHistoryList;
        }catch (SQLException e){
            System.out.println("Error: " + e);
        }
        return List.of();
    }
}
