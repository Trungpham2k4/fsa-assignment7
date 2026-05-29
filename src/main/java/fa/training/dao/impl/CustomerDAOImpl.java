package fa.training.dao.impl;

import fa.training.dao.CustomerDAO;
import fa.training.entity.Customer;
import fa.training.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM dbo.customer";
        try(Connection connection = ConnectionManager.getConnection();
            CallableStatement callableStatement = connection.prepareCall(sql)) {
            ResultSet resultSet = callableStatement.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt(1));
                customer.setCustomerName(resultSet.getString(2));
                customers.add(customer);
            }
            return customers;
        }catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return List.of();
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM dbo.customer WHERE customer_id = ?";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt(1));
                customer.setCustomerName(resultSet.getString(2));
                return customer;
            }
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO dbo.customer(customer_name) VALUES(?)";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE dbo.customer SET customer_name = ? WHERE customer_id = ?";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setInt(2, customer.getCustomerId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM dbo.customer WHERE customer_id = ?";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
