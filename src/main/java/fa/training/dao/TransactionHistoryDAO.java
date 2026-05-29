package fa.training.dao;

import fa.training.entity.TransactionHistory;

import java.util.List;

public interface TransactionHistoryDAO {
    void save(TransactionHistory transactionHistory);
    void update(TransactionHistory transactionHistory);
    void delete(int id);
    TransactionHistory findById(int id);
    List<TransactionHistory> findAll();
    List<TransactionHistory> findByUserId(int userId);

}
