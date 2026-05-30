package fa.training.dao;

import fa.training.entity.TransactionHistory;

import java.util.List;

public interface TransactionHistoryDAO extends CommonDAO<TransactionHistory> {
    int save(TransactionHistory transactionHistory);
    List<TransactionHistory> findByUserId(int userId);
}
